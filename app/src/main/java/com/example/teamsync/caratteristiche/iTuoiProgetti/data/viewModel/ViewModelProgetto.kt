package com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository.RepositoryProgetto
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.repository.RepositoryUtente
import com.example.teamsync.data.models.Priorità
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

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
    var isLoading = mutableStateOf(false)
        private set
    var carica_nome_progetto =  mutableStateOf(true)
    var cambia_lista_partecipanti =  mutableStateOf(false)

    var repositoryUtente = RepositoryUtente()

    init {
        aggiornaUtenteCorrente()
        Log.d("ViewModelProgetto", "Utente corrente: ${utenteCorrenteId.value}")
        utenteCorrenteId.value?.let {
            caricaProgettiUtente(it, false)
        }

    }

    suspend fun getnome_progetto(id_prog: String): String {
        carica_nome_progetto.value = true

        var p: Progetto? = null
        while (p == null)  {

            p = get_progetto_by_id(id_prog)
            delay(200) // Attende 0.5 secondi prima di tentare di nuovo

        }
        carica_nome_progetto.value = false
        return p.nome

    }

    fun getUtenteById(id: String, callback: (ProfiloUtente?) -> Unit) {
        viewModelScope.launch {
            try {
                val profile = repositoryUtente.getUserProfile(id)
                callback(profile)
            } catch (e: Exception) {
                Log.e(TAG, "Errore durante il recupero dell'utente con ID $id", e)
                callback(null)
            }
        }
    }


    fun aggiornaUtenteCorrente() {
        utenteCorrenteId.value = repositoryProgetto.getUtenteCorrente()?.uid
    }

    fun caricaProgettiUtente(userId: String, loadingInit: Boolean) {
        viewModelScope.launch {
            isLoading.value = loadingInit
            try {
                delay(700)
                val progettiUtente = repositoryProgetto.getProgettiUtente(userId)
                progetti.value = progettiUtente
                Log.d("ViewModelProgetto", "Progetti caricati: ${progettiUtente.size}")
            } catch (e: Exception) {
                erroreCaricamentoProgetto.value = "Errore nel caricamento dei progetti."
                Log.e("ViewModelProgetto", "Errore nel caricamento dei progetti", e)
            }finally {
                isLoading.value = false
            }
        }
    }

    fun creaProgetto(nome:String, descrizione: String, dataScadenza: Date, priorita: Priorità) {
        if (nome.isBlank()) {
            erroreAggiungiProgetto.value = "Per favore, inserisci il nome del progetto"
            return
        }
        if (dataScadenza <= Date()) {
            erroreAggiungiProgetto.value = "La data di scadenza non può essere precedente alla data di creazione del progetto."
            return
        }
        viewModelScope.launch {
            try {
                val codiceProgetto = repositoryProgetto.generaCodiceProgetto()
                val progetto = Progetto(
                    nome = nome,
                    descrizione = descrizione,
                    dataScadenza = dataScadenza,
                    priorita = priorita,
                    codice = codiceProgetto,
                    partecipanti = listOf(utenteCorrenteId.value ?: "")
                )
                val progettoId = repositoryProgetto.creaProgetto(progetto)
                Log.d("ViewModelProgetto", "Progetto aggiunto con ID: $progettoId")
                utenteCorrenteId.value?.let {
                    caricaProgettiUtente(it, false)
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
    fun aggiungiPartecipanteConCodice(userId: String,codice: String){
        viewModelScope.launch {
            try {
                val progettoId = repositoryProgetto.getProgettoIdByCodice(codice)
                val progettiUtente = repositoryProgetto.getProgettiUtente(userId)
                if(progettoId != null && !utentePartecipa(progettiUtente,progettoId)) {
                    repositoryProgetto.aggiungiPartecipante(
                        progettoId, utenteCorrenteId.value
                    )
                    aggiungiProgettoRiuscito.value = true
                    erroreAggiungiProgetto.value = null
                }else if (progettoId == null){
                    aggiungiProgettoRiuscito.value = false
                    erroreAggiungiProgetto.value = "Il codice inserito non è valido. Riprovare o contattare il creatore del progetto"
                }else{
                    aggiungiProgettoRiuscito.value = false
                    erroreAggiungiProgetto.value = "Fai già parte di questo progetto!"
                }
            }catch (e: Exception){
                aggiungiProgettoRiuscito.value = false
                erroreAggiungiProgetto.value = "Si è verificato un errore durante l'aggiunta di un progetto"
            }
        }
    }

    fun progettoScaduto(progetto: Progetto): Boolean = progetto.dataScadenza < Date()

    private fun utentePartecipa(progettiUtente: List<Progetto>, aggiungiProgettoId: String?) : Boolean{
        return try {
            for(progetto in progettiUtente){
                if(progetto.id == aggiungiProgettoId){
                    return true
                }
            }
            false
        }catch (e: Exception){
            throw e
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

    suspend fun getLista_Partecipanti(id_progetto: String): List<String> {
        return try {
            repositoryProgetto.getPartecipantiDelProgetto(id_progetto)
        } catch (e: Exception) {
            emptyList<String>()
        }
    }

    suspend fun getLista_Partecipanti(id_progetto: String, utenteCorrenteId: String?): List<String> {
        return try {
            var partecipanti = repositoryProgetto.getPartecipantiDelProgetto(id_progetto)

            utenteCorrenteId?.let { currentUser ->
                partecipanti = partecipanti.filter { partecipanteId ->
                    partecipanteId != currentUser
                }
            }
            partecipanti
        } catch (e: Exception) {
            emptyList<String>()
        }
    }

    suspend fun get_progetto_by_id(id: String) : Progetto {
        return try {
            val progetto = repositoryProgetto.getProgettoById(id)
            progetto ?: throw Exception("Progetto non trovato")
        } catch (e: Exception) {
            throw Exception("Errore durante il recupero del progetto: ${e.message}")
        }
    }
    fun aggiungiPartecipanteAlProgetto(progettoId: String, userId: String) {
        viewModelScope.launch {
            try {
                repositoryProgetto.aggiungiPartecipante(progettoId, userId)
                cambia_lista_partecipanti.value = true
                // Aggiornare l'elenco dei partecipanti del progetto dopo l'aggiunta
                // Puoi chiamare la funzione che ottiene la lista dei partecipanti qui
                // oppure aggiornare direttamente la lista se già presente nel tuo view model.
            } catch (e: Exception) {
                Log.e("ViewModelProgetto", "Errore durante l'aggiunta del partecipante al progetto", e)
                // Gestire l'errore, ad esempio, mostrando un messaggio all'utente
            }
        }
    }






}






