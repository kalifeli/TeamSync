package com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository.RepositoryProgetto
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.repository.RepositoryUtente
import com.example.teamsync.data.models.Priorità
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    var erroreAbbandonaProgetto = mutableStateOf<String?>(null)
        private set
    var progetti = mutableStateOf<List<Progetto>>(emptyList())
        private set
    var progettiCompletati = mutableStateOf<List<Progetto>>(emptyList())
        private set
    var utenteCorrenteId = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)
        private set
    var cambia_lista_partecipanti = mutableStateOf(false)

    private val _codiceProgetto = MutableLiveData<String?>()
    val codiceProgetto: LiveData<String?> get() = _codiceProgetto

    var repositoryUtente = RepositoryUtente()

    private val _carica_nome = MutableStateFlow(false)
    val carica_nome: StateFlow<Boolean> get() = _carica_nome

    init {
        aggiornaUtenteCorrente()
        Log.d("ViewModelProgetto", "Utente corrente: ${utenteCorrenteId.value}")
        utenteCorrenteId.value?.let {
            caricaProgettiUtente(it, false)
            caricaProgettiCompletatiUtente(it)
        }
    }

    fun recuperaCodiceProgetto(progettoId: String) {
        viewModelScope.launch {
            try {
                val progetto = repositoryProgetto.getProgettoById(progettoId)
                _codiceProgetto.value = progetto?.codice
            } catch (e: Exception) {
                _codiceProgetto.value = null
            }
        }
    }

    fun condividiCodiceProgetto(contesto: Context, codiceProgetto: String) {
        val inviaIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Ecco il codice per poter aggiungere il progetto: $codiceProgetto"
            )
            type = "text/plain"
        }
        val condividiIntent = Intent.createChooser(inviaIntent, null)
        contesto.startActivity(condividiIntent)
    }

    fun aggiornaProgetto(
        progettoId: String,
        nome: String,
        descrizione: String,
        dataScadenza: Date,
        priorita: Priorità,
        voto: String,
        dataConsegna: Date
    ) {
        viewModelScope.launch {
            try {
                val progetto = repositoryProgetto.getProgettoById(progettoId)
                if (progetto != null) {
                    val progettoAggiornato = progetto.copy(
                        nome = nome,
                        descrizione = descrizione,
                        dataScadenza = dataScadenza,
                        priorita = priorita,
                        voto = voto,
                        dataConsegna = dataConsegna
                    )
                    repositoryProgetto.aggiornaProgetto(progettoAggiornato)
                    utenteCorrenteId.value?.let {
                        caricaProgettiUtente(it, false)
                    }
                }
            } catch (e: Exception) {
                Log.e("ViewModelProgetto", "Errore durante l'aggiornamento del progetto", e)
            }
        }
    }

    fun aggiornaStatoProgetto(progettoId: String, completato: Boolean) {
        viewModelScope.launch {
            try {
                val progetto = repositoryProgetto.getProgettoById(progettoId)
                if (progetto != null) {
                    val progettoAggiornato = progetto.copy(completato = completato)
                    repositoryProgetto.aggiornaProgetto(progettoAggiornato)
                }
                utenteCorrenteId.value?.let {
                    caricaProgettiUtente(it, true)
                }

            } catch (e: Exception) {
                Log.e(
                    "ViewModelProgetto",
                    "Errore durante l'aggiornamento dello stato del progetto",
                    e
                )
            }
        }
    }

    suspend fun getnome_progetto(id_prog: String): String {
        _carica_nome.value = true
        return try {
            var p: Progetto? = null
            while (p == null) {
                p = get_progetto_by_id(id_prog)
                delay(200) // Attende 0.5 secondi prima di tentare di nuovo
            }
            p.nome
        } catch (e: Exception) {
            "Nome progetto non disponibile"
        } finally {
            _carica_nome.value = false

            suspend fun getnome_progetto(id_prog: String): String {
                _carica_nome.value = true

                var p: Progetto? = null
                while (p == null) {

                    p = get_progetto_by_id(id_prog)
                    delay(200) // Attende 0.5 secondi prima di tentare di nuovo

                }
                _carica_nome.value = false
                return p!!.nome
            }
        }
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

    // Questa funzione permette il caricamento dei progetti di un utente nella schermata Home dell' utente
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
            } finally {
                isLoading.value = false
            }
        }
    }

    fun caricaProgettiCompletatiUtente(userId: String){
        viewModelScope.launch {
            try {
                val progetti = repositoryProgetto.getProgettiCompletatiUtente(userId)
                progettiCompletati.value = progetti
            }catch (e: Exception){
                Log.e("ViewModelProgetto", "Errore nel caricamento dei progetti completati dell'utente ${userId}", e)
            }
        }
    }

    // La funzione permette di generare un progetto con le caratteristiche scelte dal creatore
    fun caricaProgettiUtente_callback(
        userId: String,
        loadingInit: Boolean,
        callback: (List<Progetto>) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = loadingInit
            try {
                repositoryProgetto.getProgettiUtente_callback(userId) { progetti ->
                    callback(progetti)
                }
            } catch (e: Exception) {
                erroreCaricamentoProgetto.value = "Errore nel caricamento dei progetti."
                callback(emptyList()) // Callback con lista vuota in caso di errore
            } finally {
                isLoading.value = false
            }
        }
    }




    suspend fun getProgettiUtenteByIdUtente(
                userId: String,
                callback: (List<Progetto>, String?) -> Unit
            ) {
                try {
                    val progettiUtente = repositoryProgetto.getProgettiUtente(userId)
                    callback(progettiUtente, null)
                } catch (e: Exception) {
                    callback(emptyList(), "Errore durante il recupero dei progetti: ${e.message}")
                }
            }

    fun creaProgetto(
        nome: String,
        descrizione: String,
        dataScadenza: Date,
        priorita: Priorità
    ) {
        if (nome.isBlank()) {
            erroreAggiungiProgetto.value = "Per favore, inserisci il nome del progetto"
            return
        }
        if (dataScadenza <= Date()) {
            erroreAggiungiProgetto.value =
                "La data di scadenza non può essere precedente alla data di creazione del progetto."
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
                erroreAggiungiProgetto.value =
                    "Si è verificato un errore durante la creazione del progetto. Riprovare."
                Log.e("ViewModelProgetto", "Errore durante la creazione del progetto", e)
            }
        }
    }

    fun aggiungiPartecipanteConCodice(userId: String, codice: String) {
        viewModelScope.launch {
            try {
                val progettoId = repositoryProgetto.getProgettoIdByCodice(codice)
                val progettiUtente = repositoryProgetto.getProgettiUtente(userId)
                if (progettoId != null && !utentePartecipa(progettiUtente, progettoId)) {
                    repositoryProgetto.aggiungiPartecipante(
                        progettoId, utenteCorrenteId.value
                    )
                    aggiungiProgettoRiuscito.value = true
                    erroreAggiungiProgetto.value = null
                } else if (progettoId == null) {
                    aggiungiProgettoRiuscito.value = false
                    erroreAggiungiProgetto.value =
                        "Il codice inserito non è valido. Riprovare o contattare il creatore del progetto"
                } else {
                    aggiungiProgettoRiuscito.value = false
                    erroreAggiungiProgetto.value = "Fai già parte di questo progetto!"
                }
            } catch (e: Exception) {
                aggiungiProgettoRiuscito.value = false
                erroreAggiungiProgetto.value =
                    "Si è verificato un errore durante l'aggiunta di un progetto"
            }
        }
    }

            // Questa funzione consente ad un utente di abbandonare un progetto
            fun abbandonaProgetto(userId: String?, progettoId: String) {
                viewModelScope.launch {
                    try {
                        repositoryProgetto.abbandonaProgetto(userId, progettoId) { vuoto ->
                            if (vuoto)
                                elimina_notifiche(progettoId)

                        erroreAbbandonaProgetto.value = null

                    }
                    } catch (e: Exception) {
                        erroreAbbandonaProgetto.value =
                            "Si è verificato un errore. Per favore riprovare"
                    }
                }
            }

    fun elimina_notifiche(progettoId: String) {
        viewModelScope.launch {
            try {
                repositoryProgetto.eliminaNotificheDelProgetto(progettoId)
            } catch (e: Exception) {
                Log.e("ViewModelProgetto", "Errore durante l'eliminazione delle notifiche", e)
            }
        }
    }


    // Questa funzione ha il compito di verificare se un progetto è scaduto tornando un valore booleano. True nel caso in cui il progetto è scaduto, altrimenti False.
    fun progettoScaduto(progetto: Progetto): Boolean = progetto.dataScadenza < Date()
    fun utentePartecipa(
        progettiUtente: List<Progetto>,
        aggiungiProgettoId: String?
    ): Boolean {
        return try {
            for (progetto in progettiUtente) {
                if (progetto.id == aggiungiProgettoId) {
                    return true
                }
            }
            false
        } catch (e: Exception) {
            throw e
        }
    }

    /*
       Questa funzione resetta il messaggio di errore di aggiunta di un progetto
    */
    fun resetErroreAggiungiProgetto() {
        erroreAggiungiProgetto.value = null
    }

    fun resetAggiugniProgettoRiuscito() {
        aggiungiProgettoRiuscito.value = false
    }

    fun logout() {
        try {
            viewModelScope.launch {
                repositoryProgetto.logout()
                utenteCorrenteId.value = null
                Log.d("ViewModel", "utente corrente: ${utenteCorrenteId.value}")
            }
        } catch (e: Exception) {
            Log.e("ViewModelProgetto", "Errore durante il logout", e)
        }
    }

    suspend fun getLista_Partecipanti(id_progetto: String): List<String> {
        return try {
            repositoryProgetto.getPartecipantiDelProgetto(id_progetto)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getLista_Partecipanti(
        id_progetto: String,
        callback: (List<String>) -> Unit
    ) {
        try {
            val partecipanti = repositoryProgetto.getPartecipantiDelProgetto(id_progetto)
            callback(partecipanti)
        } catch (e: Exception) {
            callback(emptyList())
        }
    }

    suspend fun getLista_Partecipanti(
        id_progetto: String,
        utenteCorrenteId: String?
    ): List<String> {
        return try {
            var partecipanti = repositoryProgetto.getPartecipantiDelProgetto(id_progetto)
            utenteCorrenteId?.let { currentUser ->
                partecipanti = partecipanti.filter { partecipanteId ->
                    partecipanteId != currentUser
                }
            }
            partecipanti
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun get_progetto_by_id(id: String): Progetto {
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
            } catch (e: Exception) {
                Log.e(
                    "ViewModelProgetto",
                    "Errore durante l'aggiunta del partecipante al progetto",
                    e
                )
                // Gestire l'errore, ad esempio, mostrando un messaggio all'utente
            }
        }
    }
}