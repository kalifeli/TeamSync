package com.example.teamsync.caratteristiche.login.data.viewModel

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.repository.EmailAlreadyInUseException
import com.example.teamsync.caratteristiche.login.data.repository.ImageUploader
import com.example.teamsync.caratteristiche.login.data.repository.RepositoryUtente
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

class ViewModelUtente : ViewModel() {

    //istanza della classe repository dell'utente
    private val repositoryUtente = RepositoryUtente()



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
    var primoAccesso = mutableStateOf(false)
        private set
    var erroreRegistrazione = mutableStateOf<String?>(null)
        private set
    var erroreVerificaEmail = mutableStateOf<String?>(null)
        private set
    private var tentativiLoginFalliti = mutableIntStateOf(0)

    private val gestore_immagine = ImageUploader()

    var userProfile: ProfiloUtente? = null

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
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            erroreLogin.value = "L'indirizzo email inserito non è valido."
            return
        }
        if (password.isBlank()){
            erroreLogin.value = "Per favore, inserisci la password."
            return
        }
        if(tentativiLoginFalliti.intValue > 3){
            erroreLogin.value = "Hai dimenticato la password?"
            return
        }

        viewModelScope.launch {
            try {
                val utente = repositoryUtente.login(email, password)
                if(utente != null && repositoryUtente.isEmailVerified()){
                    utenteCorrente = utente
                    erroreLogin.value = null
                    val isFirstLogin = repositoryUtente.isFirstLogin(utente.uid)
                    primoAccesso.value = isFirstLogin
                    loginRiuscito.value = true
                    Log.d("ViewModelUtente", "primoAccesso : ${primoAccesso.value}")
                }else{
                    erroreLogin.value = "L'email non è stata verificata. Per favore, verifica il tuo indirizzo email."
                }
            }catch (e: Exception){
                tentativiLoginFalliti.intValue += 1
                erroreLogin.value = "Email o password errate. Controlla le tue credenziali e riprova."
            }
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
        dataNascita: String, // TODO cambia in Date
        password: String,
        confermaPassword: String
    ) {
        val errore = validateRegistrationField(matricola, nome, cognome, email, dataNascita, password, confermaPassword)
        if(errore != null){
            erroreRegistrazione.value = errore
            return
        }

        viewModelScope.launch {
            try {
                val utente = repositoryUtente.signUp(matricola, nome, cognome, dataNascita, email, password)
                sendEmailVerification()
                erroreRegistrazione.value = null
                registrazioneRiuscita.value = true
            }catch (e: EmailAlreadyInUseException){
                erroreRegistrazione.value = e.message
                registrazioneRiuscita.value = false
            }catch (e: Exception){
                erroreRegistrazione.value = "Registrazione fallita. Riprovare."
                registrazioneRiuscita.value = false
            }
        }

    }
    private fun validateRegistrationField(
        matricola: String,
        nome: String,
        cognome: String,
        email: String,
        dataNascita: String, // TODO cambia in Date
        password: String,
        confermaPassword: String

    ): String?{
        return when {
            matricola.isBlank() -> "Per favore, inserisci il numero di matricola."
            email.isBlank() -> "Per favore, inserisci il tuo indirizzo email."
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "L'indirizzo email inserito non è valido."
            nome.isBlank() -> "Per favore, inserisci il tuo nome."
            cognome.isBlank() -> "Per favore, inserisci il tuo cognome."
            password.isBlank() -> "Per favore, inserisci la password."
            confermaPassword.isBlank() -> "Per favore, conferma la password inserita."
            !password.equals(confermaPassword) -> "Le password non coincidono."

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
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
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


     fun getUserProfile() {
        viewModelScope.launch {
            val currentUser = repositoryUtente.getUtenteAttuale()
            currentUser?.let {

                val profile = repositoryUtente.getUserProfile(it.uid)

                userProfile = profile
            }
        }
    }



    fun ottieni_utente(id: String, callback: (ProfiloUtente?) -> Unit) {
        repositoryUtente.get_user_sincrono(id) { profile ->
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
            repositoryUtente.updateUserProfile(profiloUtente)
            reloadUserProfile(profiloUtente.id)
        }
    }

    private fun reloadUserProfile(userId: String) {
        viewModelScope.launch {
            val profile = repositoryUtente.getUserProfile(userId)
            userProfile = profile
        }
    }
    fun signOut() {
        repositoryUtente.signOut()
    }



    fun filterAmici(query: String, callback: UserProfileCallback) {
        var risultati = mutableListOf<String>()

        val lista_nomi = mutableListOf<String>()
        val lista_cognomi = mutableListOf<String>()
        val lista_matricole = mutableListOf<String>()
        val lista_email = mutableListOf<String>()

        viewModelScope.launch {
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
        val id = userProfile?.id

        if(lista_elementi.contains(id))
            return lista_elementi.filter { it != id }
        else
            return lista_elementi
    }
    }



typealias UserProfileCallback = (List<String>) -> Unit












