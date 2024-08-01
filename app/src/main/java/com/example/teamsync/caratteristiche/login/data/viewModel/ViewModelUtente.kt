package com.example.teamsync.caratteristiche.login.data.viewModel

import android.content.ContentValues.TAG
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
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.model.SessoUtente
import com.example.teamsync.caratteristiche.login.data.repository.EmailAlreadyInUseException
import com.example.teamsync.caratteristiche.login.data.repository.ImageUploader
import com.example.teamsync.caratteristiche.login.data.repository.RepositoryUtente
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.util.Date


class ViewModelUtente(private val repositoryUtente : RepositoryUtente) : ViewModel() {

    var utenteCorrente by mutableStateOf<FirebaseUser?>(null)
        private set
    var loginRiuscito = mutableStateOf(false)
        private set
    var registrazioneRiuscita = mutableStateOf(false)
        private set
    var resetPasswordRiuscito = mutableStateOf(false)
        private set
    var erroreResetPassword = mutableStateOf<String?>(null)
        private set
    var erroreLogin = mutableStateOf<String?>(null)
        private set
    var erroreAggiornaProfilo = mutableStateOf<String?>(null)
        private set
    var aggiornaProfiloRiuscito = mutableStateOf(false)
        private set
    var primoAccesso = mutableStateOf(false)
        private set
    var erroreRegistrazione = mutableStateOf<String?>(null)
        private set
    var erroreVerificaEmail = mutableStateOf<String?>(null)
        private set
    private val _erroreEliminazioneUtente = MutableLiveData<String?>(null)
    val erroreEliminazioneUtente: LiveData<String?> get() = _erroreEliminazioneUtente
    private var tentativiLoginFalliti = mutableIntStateOf(0)

    private val gestore_immagine = ImageUploader()


    // Profilo utente autentificato
    private val _userProfilo = MutableLiveData<ProfiloUtente?>()
    val userProfilo: LiveData<ProfiloUtente?> get() = _userProfilo

    // Profilo collega
    private val _profiloCollega = MutableLiveData<ProfiloUtente?>()
    val profiloCollega: LiveData<ProfiloUtente?> get() = _profiloCollega

    private val _isLoading = MutableLiveData(false)
    val isLoading : LiveData<Boolean> get() = _isLoading

    init {
        getUserProfile()
    }

    fun login(
        email: String,
        password: String,
    ) {
        if(email.isBlank()){
            erroreLogin.value = "Per favore, inserisci l'indirizzo email."
            return
        }
        if(!EmailValidator.isValidEmail(email)){
            erroreLogin.value = "L'indirizzo email inserito non è valido."
            return
        }
        if (password.isBlank()){
            erroreLogin.value = "Per favore, inserisci la password."
            return
        }

        viewModelScope.launch {
            try {
                val utente = repositoryUtente.login(email, password)
                println("Login restituito utente: $utente")
                val isEmailVerified = repositoryUtente.isEmailVerified()
                println("Email verificata: $isEmailVerified")
                if(utente != null && isEmailVerified){
                    utenteCorrente = utente
                    erroreLogin.value = null
                    val isFirstLogin = repositoryUtente.isFirstLogin(utente.uid)
                    println("Primo accesso: $isFirstLogin")
                    primoAccesso.value = isFirstLogin
                    loginRiuscito.value = true
                    tentativiLoginFalliti.intValue = 0 // reset del contatore in caso di login riuscito
                    println("Login riuscito per l'utente: ${utente.uid}")
                }else{
                    erroreLogin.value = "L'email non è stata verificata. Per favore, verifica il tuo indirizzo email."
                    println("L'email non è stata verificata per l'utente: ${utente?.uid}")
                }
            }catch (e: Exception){
                tentativiLoginFalliti.intValue += 1
                if (tentativiLoginFalliti.intValue > 3) {
                    erroreLogin.value = "Hai dimenticato la password?"
                } else {
                    erroreLogin.value = "Email o password errate. Controlla le tue credenziali e riprova."
                }
                println("Errore durante il login: ${e.message}")
                e.printStackTrace()
            }
        }

    }

    object EmailValidator {
        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    fun aggiorna_foto_profilo(uri: Uri) : Task<Uri>
    {
        return gestore_immagine.uploadImageToFirebaseStorage(uri = uri)
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
                repositoryUtente.signUp(matricola, nome, cognome, dataNascita,sesso, email, password)
                sendEmailVerification()
                erroreRegistrazione.value = null
                registrazioneRiuscita.value = true
            }catch (e: EmailAlreadyInUseException){
                erroreRegistrazione.value = e.message
                registrazioneRiuscita.value = false
            }catch (e: Exception){
                erroreRegistrazione.value = "Registrazione fallita. Riprovare."
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
    fun validateRegistrationField(
        matricola: String,
        nome: String,
        cognome: String,
        email: String,
        dataNascita: Date,
        password: String,
        confermaPassword: String
    ): String? {
        return when {
            // Controlla se il campo matricola è vuoto
            matricola.isBlank() -> "Per favore, inserisci il numero di matricola."
            // Controlla se il campo email è vuoto
            email.isBlank() -> "Per favore, inserisci il tuo indirizzo email."
            // Controlla se l'email è valida
            !EmailValidator.isValidEmail(email) -> "L'indirizzo email inserito non è valido."
            // Controlla se il campo nome è vuoto
            nome.isBlank() -> "Per favore, inserisci il tuo nome."
            // Controlla se il campo cognome è vuoto
            cognome.isBlank() -> "Per favore, inserisci il tuo cognome."
            // Controlla se la data di nascita è nel futuro o uguale alla data attuale
            dataNascita >= Date() -> "Per favore, inserisci una data di nascita valida."
            // Controlla se il campo password è vuoto
            password.isBlank() -> "Per favore, inserisci la password."
            // Controlla se il campo confermaPassword è vuoto
            confermaPassword.isBlank() -> "Per favore, conferma la password inserita."
            // Controlla se la password e la conferma password coincidono
            !password.equals(confermaPassword) -> "Le password non coincidono."
            // Se tutti i campi sono validi, restituisce null
            else -> null
        }
    }


    private fun sendEmailVerification() {
        viewModelScope.launch {
            utenteCorrente = repositoryUtente.getUtenteAttuale()
            try {
                if (!(utenteCorrente!!.isEmailVerified)) {
                    repositoryUtente.sendEmailVerification()
                    erroreVerificaEmail.value = null
                }
            }catch (e: Exception){
                erroreVerificaEmail.value = "Si è verificato un errore durante la conferma dell'email."
            }
        }
    }
    fun updateFirstLogin() {
        viewModelScope.launch {
            utenteCorrente?.let {
                repositoryUtente.updateFirstLogin(it.uid)
                primoAccesso.value = false
            }
        }
    }

    fun resetPassword(email: String){

        if(email.isNotBlank()) {
            if(!EmailValidator.isValidEmail(email)){
                resetPasswordRiuscito.value = false
                erroreResetPassword.value = "L'indirizzo email inserito non è valido."
                return
            }
            viewModelScope.launch {
                try {
                    repositoryUtente.resetPassword(email)
                    resetPasswordRiuscito.value = true
                    erroreResetPassword.value = null
                }catch (e: Exception){
                    resetPasswordRiuscito.value = false
                    erroreResetPassword.value = "Reset della password fallito. Riprova."
                    Log.d("Login", "reset password fallito", e)
                }
            }
        }else{
            erroreResetPassword.value = "Per favore, inserisci l'indirizzo email."
        }
    }
    fun resetErroreLogin(){
        erroreLogin.value = null
    }

    fun resetLoginRiuscito(){
        loginRiuscito.value = false
    }

    fun resetErroreRegistrazione(){
        erroreRegistrazione.value = null
    }

    fun resetRegistrazioneRiuscita(){
        registrazioneRiuscita.value = false
    }

    fun resetErroreVerificaEmail(){
        erroreVerificaEmail.value = null
    }

    fun resetAggiornaProfiloRiuscito(){
        aggiornaProfiloRiuscito.value = false
    }


     fun getUserProfile() {
         viewModelScope.launch {
             try {
                 val currentUser = repositoryUtente.getUtenteAttuale()
                 currentUser?.let {
                     val profile = repositoryUtente.getUserProfile(it.uid)
                     _userProfilo.value = profile
                 }
             } catch (e: Exception) {
                 erroreAggiornaProfilo.value = "Errore durante il caricamento del profilo: ${e.message}"
             }
         }
    }


    fun ottieni_utente(id: String, callback: (ProfiloUtente?) -> Unit) {
        repositoryUtente.getUserSincrono(id) { profile ->
            callback(profile)
        }
    }



    fun finisci_amicizia(id_u: String, id_a: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Rimuovi id_u dalla lista degli amici di id_a
                repositoryUtente.rimuoviAmico(id_a, id_u)

                // Rimuovi id_a dalla lista degli amici di id_u
                repositoryUtente.rimuoviAmico(id_u, id_a)

                // Callback di successo
                onSuccess()

                // Esegui altre azioni se necessario dopo aver rimosso l'amicizia
            } catch (e: Exception) {
                Log.e("ViewModelUtente", "Errore durante il tentativo di rimuovere l'amicizia", e)
                // Gestisci l'errore se necessario
            }
        }
    }

    fun sonoAmici(idUtente2: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                var sonoAmici = userProfilo.value?.amici?.contains(idUtente2)
                while(userProfilo.value == null) {
                    sonoAmici = userProfilo.value?.amici?.contains(idUtente2)
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
    fun fai_amicizia(id_u: String, id_a: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Aggiungi id_u alla lista degli amici di id_a
                repositoryUtente.aggiungiAmico(id_a, id_u)

                // Aggiungi id_a alla lista degli amici di id_u
                repositoryUtente.aggiungiAmico(id_u, id_a)

                // Callback di successo
                onSuccess()

                // Esegui altre azioni se necessario dopo aver fatto amicizia
            } catch (e: Exception) {
                Log.e("ViewModelUtente", "Errore durante il tentativo di fare amicizia", e)
                // Gestisci l'errore se necessario
            }
        }
    }




    fun updateUserProfile(profiloUtente: ProfiloUtente) {
        viewModelScope.launch {
            try {
                if (profiloUtente.dataDiNascita.before(Date())) {
                    repositoryUtente.updateUserProfile(profiloUtente)
                    reloadUserProfile(profiloUtente.id)
                    erroreAggiornaProfilo.value = null
                    aggiornaProfiloRiuscito.value = true
                } else {
                    erroreAggiornaProfilo.value = "Per favore, inserisci una data di nascita corretta"
                    aggiornaProfiloRiuscito.value = false
                }
            } catch (e: Exception) {
                erroreAggiornaProfilo.value = "Errore nell'aggiornamento del profilo: ${e.message}"
                aggiornaProfiloRiuscito.value = false
            }
        }
    }

    fun resetErroreAggiornaProfilo(){
        erroreAggiornaProfilo.value = null
    }


    private fun reloadUserProfile(userId: String) {
        viewModelScope.launch {
            try {
                val profile = repositoryUtente.getUserProfile(userId)
                _userProfilo.value = profile
            } catch (e: Exception) {
                erroreAggiornaProfilo.value = "Errore durante il ricaricamento del profilo: ${e.message}"
            }
        }
    }
    fun signOut() {
        repositoryUtente.signOut()
    }

    fun eliminaAccount(userId: String){
        viewModelScope.launch {
            val risultato = repositoryUtente.eliminaAccount(userId)
            _erroreEliminazioneUtente.value = risultato
            signOut()
        }
    }



    fun filterAmici(query: String, callback: UserProfileCallback) {
        val risultati = mutableListOf<String>()

        val lista_nomi = mutableListOf<String>()
        val lista_cognomi = mutableListOf<String>()
        val lista_matricole = mutableListOf<String>()
        val lista_email = mutableListOf<String>()

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = repositoryUtente.getallutenti()

                for (persona in lista) {
                    val oggetto_persona = repositoryUtente.getUserProfile(persona)
                    Log.d("SIngola Persona", "Id: $oggetto_persona")

                    lista_nomi.add(oggetto_persona?.nome.toString())
                    lista_email.add(oggetto_persona?.email.toString())
                    lista_cognomi.add(oggetto_persona?.cognome.toString())
                    lista_matricole.add(oggetto_persona?.matricola.toString())


                }

                aggiungiElementiFiltrati(lista_nomi,lista, risultati, query)
                aggiungiElementiFiltrati(lista_email,lista, risultati, query)
                aggiungiElementiFiltrati(lista_cognomi,lista, risultati, query)
                aggiungiElementiFiltrati(lista_matricole,lista, risultati, query)

                callback(rimuovi_self(risultati))
            }catch (e: Exception) {
                Log.e(TAG, "Errore durante il recupero degli utenti", e)
            }finally {
                _isLoading.value = false
            }
        }

    }
    fun aggiungiElementiFiltrati(
        lista_elementi: List<String>,
        lista_id: List<String>,
        risultati: MutableList<String>,

        query: String
    ) {
        var contatore = 0
        for (elemento in lista_elementi) {
            if (elemento.contains(query, ignoreCase = true) && !risultati.contains(lista_id[contatore])) {
                risultati.add(lista_id[contatore])
            }
            contatore++
        }
    }

    fun rimuovi_self( lista_elementi: List<String>,): List<String> {
        val id = _userProfilo.value?.id

        if(lista_elementi.contains(id))
            return lista_elementi.filter { it != id }
        else
            return lista_elementi
    }

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

    fun resetProfiloCollega(){
        _profiloCollega.value = null
    }

}

typealias UserProfileCallback = (List<String>) -> Unit












