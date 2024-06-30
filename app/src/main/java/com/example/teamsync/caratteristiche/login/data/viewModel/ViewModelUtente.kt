package com.example.teamsync.caratteristiche.login.data.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.repository.RepositoryUtente
import kotlinx.coroutines.launch

class ViewModelUtente : ViewModel() {

    //istanza della classe repository dell'utente
    private val repositoryUtente = RepositoryUtente()


    fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            val utente = repositoryUtente.signIn(email, password)
            if (utente != null) {


            } else {
                messaggioDiErrore("Email o Password Non Valida. Controlla le credenziali e riprova!")
            }
        }
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
        viewModelScope.launch {
            if (passwordCorretta(password, confermaPassword)) {
                val utente =
                    repositoryUtente.signUp(matricola, nome, cognome, dataNascita, email, password)
                invioVerificaEmail("it")
            } else {
                messaggioDiErrore("Le password che hai immesso non coincidono!")
            }

        }

    }

    fun messaggioDiErrore(messaggio: String): String {
        return messaggio
    }

    private fun invioVerificaEmail(lingua: String) {
        viewModelScope.launch {
            val utenteCorrente = repositoryUtente.getUtenteAttuale()
            if (!(utenteCorrente!!.isEmailVerified)) {
                repositoryUtente.setPreferenzeLinguaEmail(lingua)
                utenteCorrente.sendEmailVerification()
                    .addOnCanceledListener {
                        Log.w("Registrazione", "email inviata correttamente")
                    }
                    .addOnFailureListener {
                        Log.w("Registrazione", "email non inviata correttamente")
                    }
            }
        }
    }

    private fun resetPassword(){


    }

    private fun passwordCorretta(password: String, confermaPassword: String): Boolean {
        if (password.equals(confermaPassword))
            return true

        return false
    }


    // COS'E'??????
    var userProfile by mutableStateOf<ProfiloUtente?>(null)
        private set

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            val currentUser = repositoryUtente.getUtenteAttuale()
            currentUser?.let {
                val profile = repositoryUtente.getUserProfile(it.uid)
                userProfile = profile
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

}


