package com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.autentificazione.data.viewModel.ViewModelUtente
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository.RepositoryProgetto
import com.example.teamsync.caratteristiche.leMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.util.Priorita
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel per gestire i dati e le operazioni relative ai progetti dell'utente.
 */
class ViewModelProgetto(
    private val repositoryProgetto: RepositoryProgetto,
    private val repositoryLeMieAttivita : ToDoRepository,
    viewModelUtente: ViewModelUtente,
    private val contesto: Context
) : ViewModel() {

    /**
     * Indica se l'aggiunta di un progetto è riuscita.
     */
    var aggiungiProgettoRiuscito = mutableStateOf(false)
        private set

    /**
     * Messaggio di errore per l'aggiunta di un progetto.
     */

    var erroreAggiungiProgetto = mutableStateOf<String?>(null)
        private set


    /**
     * Messaggio di errore per il caricamento dei progetti.
     */
    var erroreCaricamentoProgetto = mutableStateOf<String?>(null)
        private set

    /**
     * Messaggio di errore per l'abbandono del progetto.
     */
    private val  _erroreAbbandonaProgetto = MutableLiveData<String?>(null)
    val erroreAbbandonaProgetto: LiveData<String?> get() = _erroreAbbandonaProgetto

    /**
     * Indica se un progetto è stato abbandonato con successo.
     */
    private val _abbandonaProgettoRiuscito = MutableLiveData(false)
    val abbandonaProgettoRiuscito: LiveData<Boolean> get() = _abbandonaProgettoRiuscito

    /**
     * Lista dei progetti completati.
     */
    var progettiCompletati = mutableStateOf<List<Progetto>>(emptyList())
        private set

    /**
     * ID dell'utente corrente.
     */
    var utenteCorrenteId = mutableStateOf<String?>(null)

    /**
     *  Variabile che gestisce il caricamento delle operazioni asincrone
     */
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Indica se la lista dei partecipanti è stata cambiata.
     */
    var cambiaListaPartecipanti = mutableStateOf(false)

    /**
     * Codice di un progetto.
     */
    private val _codiceProgetto = MutableLiveData<String?>()
    val codiceProgetto: LiveData<String?> get() = _codiceProgetto


    /**
     * Indica se il nome di un progetto è sttao caricato.
     */
    private val _caricaNome = MutableLiveData(false)
    val caricaNome: LiveData<Boolean> get() = _caricaNome


    /**
     * Lista dei progetti di un utente autentificato.
     */
    private val _progetti = MutableLiveData<List<Progetto>>()
    val progetti: LiveData<List<Progetto>> get() = _progetti


    /**
     * Lista delle attività di un progetto.
     */
    private val _attivitaProgetti = MutableLiveData<Map<String, Int>>()
    val attivitaProgetti: LiveData<Map<String, Int>> get() = _attivitaProgetti

    /**
     * Lista dei progetti di un collega.
     */
    private val _progettiCollega = MutableLiveData<List<Progetto>>()
    val progettiCollega: LiveData<List<Progetto>> get() = _progettiCollega

    private val _erroreModificaProgetto = MutableLiveData<String?>()
    val erroreModificaProgeto: LiveData<String?> get() = _erroreModificaProgetto


    /**
     * Inizializza il ViewModel e carica i dati dell'utente corrente.
     */
    init {
        viewModelUtente.userProfilo.observeForever { userProfile ->
            if (userProfile != null) {
                utenteCorrenteId.value = userProfile.id
                caricaProgettiUtente(userProfile.id, true)
                caricaProgettiCompletatiUtente(userProfile.id)
            } else {
                Log.e("ViewModelProgetto", "Impossibile caricare i progetti, utente non trovato.")
            }
        }
    }

    /**
     * Recupera il codice del progetto dato l'ID del progetto.
     *
     * @param progettoId L'ID del progetto da recuperare.
     */
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

    /**
     * Condivide il codice del progetto tramite un Intent.
     *
     * @param contesto Il contesto da cui viene inviato l'Intent.
     * @param codiceProgetto Il codice del progetto da condividere.
     */
    fun condividiCodiceProgetto(codiceProgetto: String) {
        val messaggio = contesto.getString(R.string.messaggioCondividiProgetto, codiceProgetto)
        val inviaIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, messaggio)
            type = "text/plain"
        }
        val condividiIntent = Intent.createChooser(inviaIntent, null)
        contesto.startActivity(condividiIntent)
    }

    /**
     * Aggiorna le informazioni di un progetto esistente.
     *
     * @param progettoId L'ID del progetto da aggiornare.
     * @param nome Il nuovo nome del progetto.
     * @param descrizione La nuova descrizione del progetto.
     * @param dataScadenza La nuova data di scadenza del progetto.
     * @param priorita La nuova priorità del progetto.
     * @param voto Il nuovo voto del progetto.
     * @param dataConsegna La nuova data di consegna del progetto.
     */
    fun aggiornaProgetto(
        progettoId: String,
        nome: String,
        descrizione: String,
        dataScadenza: Date,
        priorita: Priorita,
        voto: String,
        dataConsegna: Date,
        dataCreazione: Date
    ) {
        if (nome.isBlank()) {
            _erroreModificaProgetto.value = contesto.getString(R.string.errore_nome_progetto_vuoto)
            return
        }
        if (dataScadenza.before(dataCreazione)) {
            _erroreModificaProgetto.value = contesto.getString(R.string.errore_data_scadenza_invalid)
            return
        }

        if(dataConsegna.before(dataCreazione)){
            _erroreModificaProgetto.value = contesto.getString(R.string.errore_data_consegna_invalid)
            return
        }


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
                    _erroreModificaProgetto.value = null
                }
            } catch (e: Exception) {
                _erroreModificaProgetto.value = contesto.getString(R.string.errore_aggiornamento_progetto)
            }
        }
    }

    fun resetErroreModificaProgetto(){
        _erroreModificaProgetto.value = null
    }

    /**
     * Aggiorna lo stato di completamento di un progetto.
     *
     * @param progettoId L'ID del progetto da aggiornare.
     * @param completato Il nuovo stato di completamento del progetto.
     */
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

    /**
     * Recupera il nome di un progetto dato il suo ID.
     *
     * @param idProg L'ID del progetto.
     * @return Il nome del progetto.
     */
    suspend fun getnomeProgetto(idProg: String): String {
        _caricaNome.value = true
        return try {
            var p: Progetto? = null
            while (p == null) {
                p = getProgettoById(idProg)
                delay(200) // Attende 0.5 secondi prima di tentare di nuovo
            }
            p.nome
        } catch (e: Exception) {
            contesto.getString(R.string.errore_progetto_non_trovato)
        } finally {
            _caricaNome.value = false
        }
    }

    /**
     * Aggiorna l'utente corrente.
     */
    fun aggiornaUtenteCorrente() {
        viewModelScope.launch {
            try {
                val user = repositoryProgetto.getUtenteCorrente()
                if (user != null) {
                    utenteCorrenteId.value = user.uid
                } else {
                    Log.e("ViewModelProgetto", "Utente corrente non trovato")
                }
            } catch (e: Exception) {
                Log.e("ViewModelProgetto", "Errore durante l'aggiornamento dell'utente corrente", e)
            }
        }
    }

    /**
     * Carica i progetti dell'utente.
     *
     * @param userId L'ID dell'utente.
     * @param loadingInit Indica se il caricamento è iniziato.
     */
    fun caricaProgettiUtente(userId: String, loadingInit: Boolean){
        if (userId.isBlank()) {
            Log.e("ViewModelProgetto", "ID utente mancante, impossibile caricare i progetti.")
            return
        }

        viewModelScope.launch {
            _isLoading.value = loadingInit
            try {
                val progetti = repositoryProgetto.getProgettiUtente(userId)
                _progetti.value = progetti

                // carichiamo le attività di ogni progetto
                val attivitaMap = mutableMapOf<String, Int>()
                for (progetto in progetti) {
                    val attivitaNonCompletate = repositoryLeMieAttivita.countNonCompletedTodoByProject(progetto.id ?: "")
                    attivitaMap[progetto.id ?: ""] = attivitaNonCompletate
                }
                _attivitaProgetti.value = attivitaMap
                erroreCaricamentoProgetto.value = null
            } catch (e: Exception) {
                Log.e("ViewModelProgetto", "Errore nel caricamento dei progetti", e)
                erroreCaricamentoProgetto.value = "Errore nel caricamento dei progetti."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetErroreCaricamentoProgetto(){
        erroreCaricamentoProgetto.value = null
    }

    /**
     * Carica i progetti del collega.
     *
     * @param userId L'ID del collega.
     * @param loadingInit Indica se il caricamento è iniziato.
     */
    fun caricaProgettiCollega(userId: String, loadingInit: Boolean) {
        viewModelScope.launch {
            _isLoading.value = loadingInit
            try {
                val progetti = repositoryProgetto.getProgettiUtente(userId)
                _progettiCollega.value = progetti
            } catch (e: Exception) {
                Log.e("ViewModelProgetto", "Errore nel caricamento dei progetti del collega", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Resetta i progetti del collega.
     */
    fun resetProgettiCollega() {
        _progettiCollega.value = emptyList()
    }

    /**
     * Carica i progetti completati dell'utente.
     *
     * @param userId L'ID dell'utente.
     */
    fun caricaProgettiCompletatiUtente(userId: String){
        viewModelScope.launch {
            try {
                val progetti = repositoryProgetto.getProgettiCompletatiUtente(userId)
                progettiCompletati.value = progetti
            }catch (e: Exception){
                Log.e("ViewModelProgetto", "Errore nel caricamento dei progetti completati dell'utente $userId", e)
            }
        }
    }


    /**
     * Carica i progetti dell'utente con un callback.
     *
     * @param userId L'ID dell'utente.
     * @param loadingInit Indica se il caricamento è iniziato.
     * @param callback La funzione di callback da eseguire.
     */
    fun caricaprogettiutenteCallback(
        userId: String,
        loadingInit: Boolean,
        callback: (List<Progetto>) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = loadingInit
            try {
                repositoryProgetto.progettiutenteCallback(userId) { progetti ->
                    callback(progetti)
                }
            } catch (e: Exception) {
                erroreCaricamentoProgetto.value = contesto.getString(R.string.errore_caricamento_progetti)
                callback(emptyList()) // Callback con lista vuota in caso di errore
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Recupera i progetti dell'utente per ID utente con callback.
     *
     * @param userId L'ID dell'utente.
     * @param callback La funzione di callback da eseguire.
     */
    suspend fun getProgettiUtenteByIdUtente(
                userId: String,
                callback: (List<Progetto>, String?) -> Unit,
    ) {
        try {
            val progettiUtente = repositoryProgetto.getProgettiUtente(userId)
            callback(progettiUtente, null)
        } catch (e: Exception) {
            callback(emptyList(), contesto.getString(R.string.errore_caricamento_progetti) )
        }
    }



    /**
     * Crea un nuovo progetto.
     *
     * @param nome Il nome del progetto.
     * @param descrizione La descrizione del progetto.
     * @param dataScadenza La data di scadenza del progetto.
     * @param priorita La priorità del progetto.
     */
    fun creaProgetto(
        nome: String,
        descrizione: String,
        dataScadenza: Date,
        priorita: Priorita
    ) {
        if (nome.isBlank()) {
            erroreAggiungiProgetto.value = contesto.getString(R.string.errore_nome_progetto_vuoto)
            return
        }
        if (dataScadenza.before(Date())) {
            erroreAggiungiProgetto.value =
                contesto.getString(R.string.errore_data_scadenza_invalid)
            return
        }
        if(utenteCorrenteId.value.isNullOrEmpty()){
            erroreAggiungiProgetto.value = contesto.getString(R.string.errore_utente_non_trovato)
            return
        }
        viewModelScope.launch {
            _isLoading.value = true //inizia il caricamento
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
                repositoryProgetto.creaProgetto(progetto)
                utenteCorrenteId.value?.let {
                    caricaProgettiUtente(it, false)
                }
                aggiungiProgettoRiuscito.value = true
                erroreAggiungiProgetto.value = null
            } catch (e: Exception) {
                aggiungiProgettoRiuscito.value = false
                erroreAggiungiProgetto.value = contesto.getString(R.string.errore_creazione_progetto)
            }finally {
                _isLoading.value = false // finisce il caricamento
            }
        }
    }

    /**
     * Aggiunge un partecipante a un progetto usando un codice.
     *
     * @param userId L'ID dell'utente.
     * @param codice Il codice del progetto.
     */
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
                        contesto.getString(R.string.errore_codice_non_valido)
                } else {
                    aggiungiProgettoRiuscito.value = false
                    erroreAggiungiProgetto.value =  contesto.getString(R.string.errore_gia_partecipante)
                }
            } catch (e: Exception) {
                aggiungiProgettoRiuscito.value = false
                erroreAggiungiProgetto.value =
                    contesto.getString(R.string.errore_aggiornamento_progetto)
            }
        }
    }

    /**
     * Permette a un utente di abbandonare un progetto.
     *
     * @param userId L'ID dell'utente.
     * @param progettoId L'ID del progetto.
     */
    fun abbandonaProgetto(userId: String?, progettoId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repositoryProgetto.abbandonaProgetto(userId, progettoId) { isProgettoVuoto ->
                    if (isProgettoVuoto) {
                        eliminaNotifiche(progettoId)
                    }
                    _erroreAbbandonaProgetto.value = null
                    _abbandonaProgettoRiuscito.value = true
                    Log.d("ViewModel", "abbandonaProgettoRiuscito post-callback: ${_abbandonaProgettoRiuscito.value}")
                }
            } catch (e: Exception) {
                _erroreAbbandonaProgetto.value = contesto.getString(R.string.errore_abbandono_progetto)
                _abbandonaProgettoRiuscito.value = false
            }finally {
                _isLoading.value = false
                Log.d("ViewModel", "abbandonaProgettoRiuscito final value: ${_abbandonaProgettoRiuscito.value}")
            }
        }
    }


    fun resetAbbandonaProgettoRiuscito(){
        _abbandonaProgettoRiuscito.value = false
    }

    fun resetErroreAbbandonaProgetto(){
        _erroreAbbandonaProgetto.value = null
    }

    /**
     * Elimina le notifiche relative a un progetto.
     *
     * @param progettoId L'ID del progetto.
     */
    private fun eliminaNotifiche(progettoId: String) {
        viewModelScope.launch {
            try {
                repositoryProgetto.eliminaNotificheDelProgetto(progettoId)
            } catch (e: Exception) {
                Log.e("ViewModelProgetto", "Errore durante l'eliminazione delle notifiche", e)
            }
        }
    }

    /**
     * Verifica se un progetto è scaduto.
     *
     * @param progetto Il progetto da verificare.
     * @return true se il progetto è scaduto, altrimenti false.
     */
    fun progettoScaduto(progetto: Progetto): Boolean = progetto.dataScadenza < Date()

    /**
     * Verifica se l'utente partecipa a un progetto.
     *
     * @param progettiUtente La lista dei progetti dell'utente.
     * @param aggiungiProgettoId L'ID del progetto da verificare.
     * @return true se l'utente partecipa al progetto, altrimenti false.
     */
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

    /**
     * Resetta il messaggio di errore per l'aggiunta di un progetto.
     */
    fun resetErroreAggiungiProgetto() {
        erroreAggiungiProgetto.value = null
    }

    /**
     * Resetta lo stato di successo per l'aggiunta di un progetto.
     */
    fun resetAggiugniProgettoRiuscito() {
        aggiungiProgettoRiuscito.value = false
    }

    /**
     * Recupera la lista dei partecipanti a un progetto.
     *
     * @param idProgetto L'ID del progetto.
     * @return La lista dei partecipanti.
     */
    suspend fun getListaPartecipanti(idProgetto: String): List<String> {
        return try {
            repositoryProgetto.getPartecipantiDelProgetto(idProgetto)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Recupera la lista dei partecipanti a un progetto con callback.
     *
     * @param idProgetto L'ID del progetto.
     * @param callback La funzione di callback da eseguire.
     */
    suspend fun getListaPartecipanti(
        idProgetto: String,
        callback: (List<String>) -> Unit
    ) {
        try {
            val partecipanti = repositoryProgetto.getPartecipantiDelProgetto(idProgetto)
            callback(partecipanti)
        } catch (e: Exception) {
            callback(emptyList())
        }
    }

    /**
     * Recupera la lista dei partecipanti a un progetto filtrata dall'utente corrente.
     *
     * @param idProgetto L'ID del progetto.
     * @param utenteCorrenteId L'ID dell'utente corrente.
     * @return La lista dei partecipanti filtrata.
     */
    suspend fun getListaPartecipanti(
        idProgetto: String,
        utenteCorrenteId: String?
    ): List<String> {
        _isLoading.value = true
        return try {
            var partecipanti = repositoryProgetto.getPartecipantiDelProgetto(idProgetto)
            utenteCorrenteId?.let { currentUser ->
                partecipanti = partecipanti.filter { partecipanteId ->
                    partecipanteId != currentUser
                }
            }
            partecipanti
        } catch (e: Exception) {
            emptyList()
        }finally {
            _isLoading.value = false
        }
    }

    /**
     * Recupera un progetto dato il suo ID.
     *
     * @param id L'ID del progetto.
     * @return Il progetto recuperato.
     * @throws Exception Se il progetto non è stato trovato.
     */
    suspend fun getProgettoById(id: String): Progetto {
        _isLoading.value = true
        return try {
            val progetto = repositoryProgetto.getProgettoById(id)
            progetto ?: throw Exception("Progetto non trovato")
        } catch (e: Exception) {
            throw Exception("Errore durante il recupero del progetto: ${e.message}")
        }finally {
            _isLoading.value = false
        }
    }

    /**
     * Aggiunge un partecipante a un progetto.
     *
     * @param progettoId L'ID del progetto.
     * @param userId L'ID dell'utente da aggiungere.
     */
    fun aggiungiPartecipante(progettoId: String, userId: String) {
        // Assicurati che progettoId sia correttamente valorizzato e che il documento esista
        if (progettoId.isNotEmpty()) {
            val progettoReference = FirebaseFirestore.getInstance().collection("progetti").document(progettoId)

            progettoReference.update("partecipanti", FieldValue.arrayUnion(userId))
                .addOnSuccessListener {
                    Log.d("RepositoryProgetto", "Utente $userId aggiunto con successo al progetto $progettoId")
                }
                .addOnFailureListener { e ->
                    Log.e("RepositoryProgetto", "Errore nell'aggiungere l'utente al progetto: ${e.message}")
                }
        } else {
            Log.e("RepositoryProgetto", "Errore: progettoId è vuoto o nullo")
        }
    }


    fun resetDatiProgetti() {
        _progetti.value = emptyList()
        _attivitaProgetti.value = emptyMap()
        progettiCompletati.value = emptyList()
        utenteCorrenteId.value = null
    }
}