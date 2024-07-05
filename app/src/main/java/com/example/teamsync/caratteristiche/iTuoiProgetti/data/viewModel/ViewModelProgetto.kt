package com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository.RepositoryProgetto
import kotlinx.coroutines.launch

class ViewModelProgetto : ViewModel() {
    private val repositoryProgetto = RepositoryProgetto()
    var aggiungiProgettoRiuscito = mutableStateOf(false)
        private set
    var erroreAggiungiProgetto = mutableStateOf<String?>(null)
        private set
    var erroreCaricamentoProgetto = mutableStateOf<String?>(null)
        private set
    var progetti = mutableStateOf<List<Progetto>>(emptyList())
        private set
    var utenteCorrenteId = mutableStateOf<String?>(null)

    init {
        aggiornaUtenteCorrente()
        Log.d("ViewModelProgetto", "Utente corrente: ${utenteCorrenteId.value}")
        utenteCorrenteId.value?.let {
            caricaProgettiUtente(it)
        }

    }
    fun aggiornaUtenteCorrente() {
        utenteCorrenteId.value = repositoryProgetto.getUtenteCorrente()?.uid
    }

    fun caricaProgettiUtente(userId: String) {
        viewModelScope.launch {
            try {
                val progettiUtente = repositoryProgetto.getProgettiUtente(userId)
                progetti.value = progettiUtente
                Log.d("ViewModelProgetto", "Progetti caricati: ${progettiUtente.size}")
            } catch (e: Exception) {
                erroreCaricamentoProgetto.value = "Errore nel caricamento dei progetti."
                Log.e("ViewModelProgetto", "Errore nel caricamento dei progetti", e)
            }
        }
    }

    fun aggiungiProgetto(progetto: Progetto) {
        if (progetto.nome.isBlank()) {
            erroreAggiungiProgetto.value = "Per favore, inserisci il nome del progetto"
            return
        }
        if (progetto.dataScadenza <= progetto.dataCreazione) {
            erroreAggiungiProgetto.value = "La data di scadenza non può essere precedente alla data di creazione del progetto."
            return
        }
        viewModelScope.launch {
            try {
                val progettoId = repositoryProgetto.aggiungiProgetto(progetto)
                Log.d("ViewModelProgetto", "Progetto aggiunto con ID: $progettoId")
                repositoryProgetto.aggiungiPartecipante(progettoId, utenteCorrenteId.value ?: "")
                utenteCorrenteId.value?.let {
                    caricaProgettiUtente(it)
                }
                aggiungiProgettoRiuscito.value = true
                erroreAggiungiProgetto.value = null
            } catch (e: Exception) {
                aggiungiProgettoRiuscito.value = false
                erroreAggiungiProgetto.value = "Si è verificato un errore durante la creazione del progetto. Riprovare."
                Log.e("ViewModelProgetto", "Errore durante la creazione del progetto", e)
            }
        }
    }

    fun resetErroreAggiungiProgetto() {
        erroreAggiungiProgetto.value = null
    }

    fun resetAggiugniProgettoRiuscito() {
        aggiungiProgettoRiuscito.value = false
    }

    fun logout(){
        try {
            viewModelScope.launch {
                repositoryProgetto.logout()
                utenteCorrenteId.value = null

                Log.d("ViewModel", "utente corrente: ${utenteCorrenteId.value}")
            }
        }catch (e: Exception){
            Log.e("ViewModelProgetto", "Errore durante il logout", e)
        }
    }
}


