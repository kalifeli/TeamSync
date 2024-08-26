package com.example.teamsync.caratteristiche.autentificazione.data.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.autentificazione.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.autentificazione.data.model.SessoUtente
import com.example.teamsync.caratteristiche.autentificazione.data.repository.EmailAlreadyInUseException
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.util.ImageUploader
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel per gestire le operazioni relative all'utente, come login, registrazione e gestione del profilo.
 */
class ViewModelUtente(private val repositoryUtente : RepositoryUtente, private val context: Context) : ViewModel() {
    /**
     * Indica l'utente corrente.
     */
    var utenteCorrente by mutableStateOf<FirebaseUser?>(null)
        private set
    /**
     * Indica se il login è riuscito.
     */
    var loginRiuscito = mutableStateOf(false)
        private set
    /**
     * Indica se la registrazione è riuscito
     */
    var registrazioneRiuscita = mutableStateOf(false)
        private set
    /**
     * Indica se il reset della password è riuscito.
     */
    var resetPasswordRiuscito = mutableStateOf(false)
        private set
    /**
     * Indica se si è verificato un errore durante il reset della password .
     */
    var erroreResetPassword = mutableStateOf<String?>(null)
        private set
    /**
     * Indica se si è verificato un errore durante il login.
     */
    var erroreLogin = mutableStateOf<String?>(null)
        private set
    /**
     * Indica se si è verificato un errore durante l'operazione di aggiornamento del profilo.
     */
    var erroreAggiornaProfilo = mutableStateOf<String?>(null)
        private set
    /**
     * Indica se l'operazione di aggiornamento del profilo è riuscita.
     */
    var aggiornaProfiloRiuscito = mutableStateOf(false)
        private set
    /**
     * Indica se è il primo accesso di un utente.
     */
    var primoAccesso = mutableStateOf(false)
        private set
    /**
     * Indica se si è verificato un errore durante la registrazione.
     */
    var erroreRegistrazione = mutableStateOf<String?>(null)
        private set
    /**
     * Indica se si è verificato un errore durante l'operazione di verifica della email.
     */
    var erroreVerificaEmail = mutableStateOf<String?>(null)
        private set
    /**
     * Indica se si è verificato un errore durante l'operazione di eliminazione di un utente.
     */
    private val _erroreEliminazioneUtente = MutableLiveData<String?>(null)
    val erroreEliminazioneUtente: LiveData<String?> get() = _erroreEliminazioneUtente

    /**
     * Indica il numero di tentativi falliti nell'effettuare il login
     */
    private var tentativiLoginFalliti = mutableIntStateOf(0)


    private val gestoreImmagine = ImageUploader()


    /**
     * Profilo utente autentificato
     */
    private val _userProfilo = MutableLiveData<ProfiloUtente?>()
    val userProfilo: LiveData<ProfiloUtente?> get() = _userProfilo

    /**
     * Profilo collega
     */
    private val _profiloCollega = MutableLiveData<ProfiloUtente?>()
    val profiloCollega: LiveData<ProfiloUtente?> get() = _profiloCollega

    /**
     * Indica lo stato di caricamento
     */
    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> get() = _isLoading

    init {
        getUserProfile()
    }


    /**
     * Effettua il login con email e password.
     *
     * @param email L'indirizzo email dell'utente.
     * @param password La password dell'utente.
     */
    fun login(
        email: String,
        password: String,
    ) {
        if(email.isBlank()){
            erroreLogin.value = context.getString(R.string.errore_inserisci_email)
            return
        }
        if(!EmailValidator.isValidEmail(email)){
            erroreLogin.value = context.getString(R.string.errore_email_non_valido)
            return
        }
        if (password.isBlank()){
            erroreLogin.value = context.getString(R.string.errore_inserisci_password)
            return
        }

        viewModelScope.launch {
            try {
                val utente = repositoryUtente.login(email, password)
                val isEmailVerified = repositoryUtente.isEmailVerified()
                if(utente != null && isEmailVerified){
                    utenteCorrente = utente
                    erroreLogin.value = null
                    val isFirstLogin = repositoryUtente.isFirstLogin(utente.uid)
                    primoAccesso.value = isFirstLogin
                    loginRiuscito.value = true
                    tentativiLoginFalliti.intValue = 0 // reset del contatore in caso di login riuscito
                    getUserProfile()
                }else{
                    erroreLogin.value = context.getString(R.string.errore_email_non_verificata)
                }
            }catch (e: Exception){
                tentativiLoginFalliti.intValue += 1
                if (tentativiLoginFalliti.intValue > 3) {
                    erroreLogin.value = context.getString(R.string.errore_password_dimenticata)
                } else {
                    erroreLogin.value = context.getString(R.string.errore_credenziali_errate)
                }
                e.printStackTrace()
            }
        }
    }

    /**
     * Valida l'indirizzo email.
     */
    object EmailValidator {
        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    /**
     * Carica una foto profilo.
     *
     * @param uri L'URI della foto da caricare.
     * @return Un task di Firebase che rappresenta l'operazione di upload.
     */
    fun aggiornaFotoProfilo(uri: Uri) : Task<Uri>
    {
        return gestoreImmagine.uploadImageToFirebaseStorage(uri = uri)
    }


    fun signUp(
        matricola: String,
        nome: String,
        cognome: String,
        email: String,
        dataNascita: Date,
        sesso: SessoUtente,
        password: String,
        confermaPassword: String
    ) {
        val errore = validateRegistrationField(matricola, nome, cognome, email, dataNascita, password, confermaPassword)

        if(errore != null){
            erroreRegistrazione.value = errore
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val utente = repositoryUtente.signUp(matricola, nome, cognome, dataNascita,sesso, email, password)
                if(utente != null){
                    utenteCorrente = utente
                    sendEmailVerification()
                    getUserProfile()
                    erroreRegistrazione.value = null
                    registrazioneRiuscita.value = true
                }
            }catch (e: EmailAlreadyInUseException){
                erroreRegistrazione.value = e.message
                registrazioneRiuscita.value = false
            }catch (e: Exception){
                erroreRegistrazione.value = context.getString(R.string.errore_registrazione_fallita)
                registrazioneRiuscita.value = false
            }finally {
                _isLoading.value = false
            }
        }

    }

    /**
     * Valida i campi di registrazione.
     *
     * Questa funzione controlla i campi di registrazione forniti e restituisce un messaggio di errore
     * se uno dei campi non è valido. Se tutti i campi sono validi, restituisce null.
     *
     * @param matricola Il numero di matricola dell'utente. Non può essere vuoto.
     * @param nome Il nome dell'utente. Non può essere vuoto.
     * @param cognome Il cognome dell'utente. Non può essere vuoto.
     * @param email L'indirizzo email dell'utente. Deve essere un indirizzo email valido.
     * @param dataNascita La data di nascita dell'utente. Deve essere una data passata rispetto alla data attuale.
     * @param password La password dell'utente. Non può essere vuota.
     * @param confermaPassword La conferma della password dell'utente. Deve corrispondere alla password.
     * @return Una stringa contenente il messaggio di errore se uno dei campi non è valido,
     *         oppure null se tutti i campi sono validi.
     *
     * @exception IllegalArgumentException Se la data di nascita è nel futuro.
     */
    private fun validateRegistrationField(
        matricola: String,
        nome: String,
        cognome: String,
        email: String,
        dataNascita: Date,
        password: String,
        confermaPassword: String
    ): String? {
        val matricolaPattern = Regex("^S\\d{7}$") // Regex per verificare che la matricola inizi con S e seguita da 7 numeri

        return when {
            // Controlla se il campo matricola è vuoto
            matricola.isBlank() -> context.getString(R.string.errore_inserisci_matricola)
            // Controlla che la matricola inserita sia valida
            !matricolaPattern.matches(matricola) -> context.getString(R.string.errore_matricola_non_valida)
            // Controlla se il campo email è vuoto
            email.isBlank() -> context.getString(R.string.errore_email_vuota)
            // Controlla se l'email è valida
            !EmailValidator.isValidEmail(email) -> context.getString(R.string.errore_email_non_valido)
            // Controlla se il campo nome è vuoto
            nome.isBlank() -> context.getString(R.string.errore_nome_vuoto)
            // Controlla se il campo cognome è vuoto
            cognome.isBlank() -> context.getString(R.string.errore_cognome_vuoto)
            // Controlla se la data di nascita è nel futuro o uguale alla data attuale
            dataNascita >= Date() -> context.getString(R.string.errore_data_nascita_non_valida)
            // Controlla se il campo password è vuoto
            password.isBlank() -> context.getString(R.string.errore_inserisci_password)
            // Controlla se il campo confermaPassword è vuoto
            confermaPassword.isBlank() -> context.getString(R.string.errore_conferma_password)
            // Controlla se la password e la conferma password coincidono
            password != confermaPassword -> context.getString(R.string.errore_password_non_corrispondenti)
            // Se tutti i campi sono validi, restituisce null
            else -> null
        }
    }

    /**
     * Invia l'email di verifica all'utente corrente.
     */
    private fun sendEmailVerification() {
        viewModelScope.launch {
            utenteCorrente = repositoryUtente.getUtenteAttuale()
            try {
                if (!(utenteCorrente!!.isEmailVerified)) {
                    repositoryUtente.sendEmailVerification()
                    erroreVerificaEmail.value = null
                }
            }catch (e: Exception){
                erroreVerificaEmail.value = context.getString(R.string.errore_verifica_email)
            }
        }
    }

    /**
     * Aggiorna lo stato del primo accesso dell'utente corrente.
     */
    fun updateFirstLogin() {
        viewModelScope.launch {
            utenteCorrente?.let {
                repositoryUtente.updateFirstLogin(it.uid)
                primoAccesso.value = false
            }
        }
    }

    /**
     * Reimposta la password dell'utente.
     *
     * @param email L'indirizzo email dell'utente.
     */
    fun resetPassword(email: String){

        if(email.isNotBlank()) {
            if(!EmailValidator.isValidEmail(email)){
                resetPasswordRiuscito.value = false
                erroreResetPassword.value = context.getString(R.string.errore_email_non_valido)
                return
            }
            viewModelScope.launch {
                try {
                    repositoryUtente.resetPassword(email)
                    resetPasswordRiuscito.value = true
                    erroreResetPassword.value = null
                }catch (e: Exception){
                    resetPasswordRiuscito.value = false
                    erroreResetPassword.value = context.getString(R.string.errore_reset_password_fallito)
                }
            }
        }else{
            erroreResetPassword.value = context.getString(R.string.errore_email_vuota)
        }
    }

    /**
     * Resetta il messaggio di errore del login.
     */
    fun resetErroreLogin(){
        erroreLogin.value = null
    }

    /**
     * Resetta lo stato del login riuscito.
     */
    fun resetLoginRiuscito(){
        loginRiuscito.value = false
    }

    /**
     * Resetta il messaggio di errore della registrazione.
     */
    fun resetErroreRegistrazione(){
        erroreRegistrazione.value = null
    }

    /**
     * Resetta lo stato della registrazione riuscita.
     */
    fun resetRegistrazioneRiuscita(){
        registrazioneRiuscita.value = false
    }

    /**
     * Resetta il messaggio di errore della verifica email.
     */
    fun resetErroreVerificaEmail(){
        erroreVerificaEmail.value = null
    }

    /**
     * Resetta il messaggio di errore della verifica email.
     */
    fun resetAggiornaProfiloRiuscito(){
        aggiornaProfiloRiuscito.value = false
    }

    /**
     * Recupera il profilo dell'utente corrente.
     */
    fun getUserProfile() {
        viewModelScope.launch {
            try {
                val currentUser = repositoryUtente.getUtenteAttuale()
                if (currentUser != null) {
                    utenteCorrente = currentUser
                    val profile = repositoryUtente.getUserProfile(currentUser.uid)
                    _userProfilo.value = profile

                    // Verifica e pulisce gli amici non esistenti
                    profile?.let {
                        val amiciPuliti = repositoryUtente.rimuoviAmiciNonEsistenti(it.id, it.amici)
                        if (amiciPuliti.size != it.amici.size) {
                            it.amici = amiciPuliti
                            repositoryUtente.updateUserProfile(it)
                        }
                    }
                } else {
                    Log.e("ViewModelUtente", "Utente non autenticato. Impossibile caricare il profilo.")
                }
            } catch (e: Exception) {
                Log.e("ViewModelUtente", "Errore durante il caricamento del profilo utente: ${e.message}")
            }
        }
    }

    /**
     * Resetta le informazioni dell'utente autentificato
     */
    private fun resetUserProfile(){
        _userProfilo.value = null
        utenteCorrente = null
    }

    /**
     * Recupera il profilo di un utente dato il suo ID.
     *
     * @param id L'ID dell'utente.
     * @param callback La funzione di callback da eseguire con il profilo dell'utente.
     */
    fun ottieniUtente(id: String, callback: (ProfiloUtente?) -> Unit) {
        repositoryUtente.getUserSincrono(id) { profile ->
            callback(profile)
        }
    }

    /**
     * Termina l'amicizia tra due utenti.
     *
     * @param idUtente L'ID del primo utente.
     * @param idAmico L'ID del secondo utente.
     * @param onSuccess La funzione di callback da eseguire in caso di successo.
     */
    fun finisciAmicizia(idUtente: String, idAmico: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Rimuovi id_u dalla lista degli amici di id_a
                repositoryUtente.rimuoviAmico(idAmico, idUtente)

                // Rimuovi id_a dalla lista degli amici di id_u
                repositoryUtente.rimuoviAmico(idUtente, idAmico)

                // Callback di successo
                onSuccess()

                // Esegui altre azioni se necessario dopo aver rimosso l'amicizia
            } catch (e: Exception) {
                Log.e("ViewModelUtente", "Errore durante il tentativo di rimuovere l'amicizia", e)
                // Gestisci l'errore se necessario
            }
        }
    }

    /**
     * Verifica se due utenti sono amici.
     *
     * @param idUtente L'ID del secondo utente.
     * @param callback La funzione di callback da eseguire con il risultato della verifica.
     */
    fun sonoAmici(idUtente: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                var sonoAmici = userProfilo.value?.amici?.contains(idUtente)
                while(userProfilo.value == null) {
                    sonoAmici = userProfilo.value?.amici?.contains(idUtente)
                }
                if (sonoAmici != null) {
                    callback(sonoAmici)
                }

            } catch (e: Exception) {
                Log.e("ViewModelUtente", "Errore durante la verifica dell'amicizia", e)
                // Se si verifica un errore, ritorna false
                callback(false)
            }
        }
    }

    /**
     * Crea un'amicizia tra due utenti.
     *
     * @param idUtente L'ID del primo utente.
     * @param idAmico L'ID del secondo utente.
     * @param onSuccess La funzione di callback da eseguire in caso di successo.
     */
    fun faiAmicizia(idUtente: String, idAmico: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Aggiungi id_u alla lista degli amici di id_a
                repositoryUtente.aggiungiAmico(idAmico, idUtente)

                // Aggiungi id_a alla lista degli amici di id_u
                repositoryUtente.aggiungiAmico(idUtente, idAmico)

                // Callback di successo
                onSuccess()

                // Esegui altre azioni se necessario dopo aver fatto amicizia
            } catch (e: Exception) {
                Log.e("ViewModelUtente", "Errore durante il tentativo di fare amicizia", e)
                // Gestisci l'errore se necessario
            }
        }
    }

    /**
     * Aggiorna il profilo di un utente.
     *
     * @param profiloUtente Il profilo aggiornato dell'utente.
     */
    fun updateUserProfile(profiloUtente: ProfiloUtente) {
        viewModelScope.launch {
            try {
                // Validazione dei campi del profilo
                val errore = validaCampiProfilo(profiloUtente)

                if (errore != null) {
                    erroreAggiornaProfilo.value = errore
                    aggiornaProfiloRiuscito.value = false
                    return@launch
                }

                repositoryUtente.updateUserProfile(profiloUtente)
                reloadUserProfile(profiloUtente.id)
                erroreAggiornaProfilo.value = null
                aggiornaProfiloRiuscito.value = true
            } catch (e: Exception) {
                erroreAggiornaProfilo.value = context.getString(R.string.errore_aggiornamento_profilo)
                aggiornaProfiloRiuscito.value = false
            }
        }
    }

    /**
     * Valida i campi del profilo utente.
     *
     * @param profiloUtente Il profilo utente da validare.
     * @return Una stringa contenente il messaggio di errore se uno dei campi non è valido,
     *         oppure null se tutti i campi sono validi.
     */
    private fun validaCampiProfilo(profiloUtente: ProfiloUtente): String? {
        val nomePattern = Regex("^[A-Za-zÀ-ÿ ']+$")
        val cognomePattern = Regex("^[A-Za-zÀ-ÿ ']+$")
        val matricolaPattern = Regex("^S\\d{7}$")

        return when {
            profiloUtente.nome.isBlank() -> context.getString(R.string.errore_nome_vuoto)
            !nomePattern.matches(profiloUtente.nome) -> context.getString(R.string.errore_nome_non_valido)
            profiloUtente.cognome.isBlank() -> context.getString(R.string.errore_cognome_vuoto)
            !cognomePattern.matches(profiloUtente.cognome) -> context.getString(R.string.errore_cognome_non_valido)
            profiloUtente.dataDiNascita >= Date() -> context.getString(R.string.errore_data_nascita_non_valida)
            profiloUtente.matricola.isBlank() -> context.getString(R.string.errore_matricola_vuota)
            !matricolaPattern.matches(profiloUtente.matricola) -> context.getString(R.string.errore_matricola_non_valida)
            profiloUtente.email.isBlank() -> context.getString(R.string.errore_email_vuota)
            !EmailValidator.isValidEmail(profiloUtente.email) -> context.getString(R.string.errore_email_non_valido)
            else -> null
        }
    }

    /**
     * Resetta il messaggio di errore dell'aggiornamento del profilo.
     */
    fun resetErroreAggiornaProfilo(){
        erroreAggiornaProfilo.value = null
    }


    /**
     * Ricarica il profilo di un utente dato il suo ID.
     *
     * @param userId L'ID dell'utente.
     */
    private fun reloadUserProfile(userId: String) {
        viewModelScope.launch {
            try {
                val profile = repositoryUtente.getUserProfile(userId)
                _userProfilo.value = profile
            } catch (e: Exception) {
                erroreAggiornaProfilo.value = context.getString(R.string.errore_caricamento_profilo_utente)
            }
        }
    }

    /**
     * Effettua il logout dell'utente.
     */
      fun signOut() {
        repositoryUtente.signOut()
        resetUserProfile()
    }

    /**
     * Elimina l'account di un utente dato il suo ID.
     *
     * @param userId L'ID dell'utente.
     */
    fun eliminaAccount(userId: String){
        viewModelScope.launch {
            val risultato = repositoryUtente.eliminaAccount(userId)
            _erroreEliminazioneUtente.value = risultato
            signOut()
        }
    }

    /**
     * Filtra gli amici dell'utente in base a una query di ricerca.
     *
     * @param query La query di ricerca.
     * @param callback La funzione di callback da eseguire con i risultati della ricerca.
     */
    fun filterAmici(query: String, callback: UserProfileCallback) {
        val risultati = mutableListOf<String>()

        val listaNomi = mutableListOf<String>()
        val listaCognomi = mutableListOf<String>()
        val listaMatricole = mutableListOf<String>()
        val listaEmail = mutableListOf<String>()

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = repositoryUtente.getAllUtenti()

                for (persona in lista) {
                    val oggettoPersona = repositoryUtente.getUserProfile(persona)

                    listaNomi.add(oggettoPersona?.nome.toString())
                    listaEmail.add(oggettoPersona?.email.toString())
                    listaCognomi.add(oggettoPersona?.cognome.toString())
                    listaMatricole.add(oggettoPersona?.matricola.toString())


                }

                aggiungiElementiFiltrati(listaNomi,lista, risultati, query)
                aggiungiElementiFiltrati(listaEmail,lista, risultati, query)
                aggiungiElementiFiltrati(listaCognomi,lista, risultati, query)
                aggiungiElementiFiltrati(listaMatricole,lista, risultati, query)

                callback(rimuoviSelf(risultati))
            }catch (e: Exception) {
                Log.e("ViewModelUtente", "Errore durante il recupero degli utenti", e)
            }finally {
                _isLoading.value = false
            }
        }

    }

    /**
     * Aggiunge gli elementi filtrati a una lista di risultati.
     *
     * @param listaElementi La lista degli elementi da filtrare.
     * @param listaId La lista degli ID associati agli elementi.
     * @param risultati La lista dei risultati.
     * @param query La query di ricerca.
     */
    private fun aggiungiElementiFiltrati(
        listaElementi: List<String>,
        listaId: List<String>,
        risultati: MutableList<String>,

        query: String
    ) {
        for ((contatore, elemento) in listaElementi.withIndex()) {
            if (elemento.contains(query, ignoreCase = true) && !risultati.contains(listaId[contatore])) {
                risultati.add(listaId[contatore])
            }
        }
    }

    /**
     * Rimuove l'ID dell'utente corrente dalla lista degli elementi.
     *
     * @param listaElementi La lista degli elementi.
     * @return La lista degli elementi senza l'ID dell'utente corrente.
     */
    private fun rimuoviSelf(listaElementi: List<String>): List<String> {
        val id = _userProfilo.value?.id

        return if(listaElementi.contains(id))
            listaElementi.filter { it != id }
        else
            listaElementi
    }

    /**
     * Recupera il profilo di un collega dato il suo ID.
     *
     * @param userId L'ID del collega.
     */
    fun ottieniCollega(userId: String){
        viewModelScope.launch {
            try {
                val profilo = repositoryUtente.getUserProfile(userId)
                _profiloCollega.value = profilo
            }catch (e: Exception){
                Log.e("ViewModelUtente", "Errore durante il recupero del profilo del collega $userId")
                _profiloCollega.value = null
            }
        }
    }


    /**
     * Recupera il profilo dell'utente corrispondente all'ID specificato.
     *
     * Questa funzione utilizza il repository per ottenere il profilo utente dal database.
     * Imposta `_isLoading` su `true` all'inizio e lo reimposta su `false` alla fine dell'operazione,
     * indipendentemente dal fatto che abbia avuto successo o meno.
     *
     * @param userId L'ID dell'utente di cui si vuole ottenere il profilo.
     * @return Un oggetto [ProfiloUtente] se il profilo è stato trovato, o `null` se si è verificato un errore o se il profilo non esiste.
     * @throws Exception Se si verifica un problema durante il recupero del profilo utente.
     */
    suspend fun getUserProfileById(userId: String): ProfiloUtente? {
        _isLoading.value = true
        return try {
            repositoryUtente.getUserProfile(userId)
        } catch (e: Exception) {
            Log.e("ViewModelUtente", "Errore nel caricamento dell'utente $userId", e)
            null
        }finally {
            _isLoading.value = false
        }
    }

}

typealias UserProfileCallback = (List<String>) -> Unit
