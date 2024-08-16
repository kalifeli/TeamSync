package com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.caratteristiche.autentificazione.data.repository.RepositoryUtente
import com.example.teamsync.caratteristiche.leMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.leMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.util.Priorita
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class LeMieAttivitaViewModel(
    private val repositoryLeMieAttivita: ToDoRepository, repositoryUtente : RepositoryUtente
) : ViewModel() {

    private val utenteId = repositoryUtente.getUtenteAttualeID()

    /**
     * Lista delle attività dell'utente.
     */
    var leMieAttivita by mutableStateOf<List<LeMieAttivita>>(emptyList())
        private set

    /**
     * Indicatore dei progressi delle task di un progetto
     */
    private val _progressione = MutableLiveData(0.0f)
    val progressione: LiveData<Float> = _progressione

    private val _attivitaCompletate = MutableLiveData(0)
    val attivitaCompletate: LiveData<Int> = _attivitaCompletate

    private val _attivitaTotali = MutableLiveData(0)
    val attivitaTotali: LiveData<Int> = _attivitaTotali

    private val _fileUri = MutableLiveData<Uri?>()
    val fileUri: LiveData<Uri?> get() = _fileUri // DA TOGLIERE

    private val _uploadResult = MutableLiveData<String?>()
    val uploadResult: LiveData<String?> get() = _uploadResult


    /**
     * Indica se il caricamento è in corso.
     */
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    /**
     * Lista delle attività non completate dell'utente.
     */
    var leMieAttivitaNonCompletate by mutableStateOf<List<LeMieAttivita>>(emptyList())

    /**
     * Lista delle attività per utente.
     */
    var leMieAttivitaCompletate by mutableStateOf<List<LeMieAttivita>>(emptyList())


    var leMieAttivitaPerUtente by mutableStateOf<List<LeMieAttivita>>(emptyList())

    /**
     * Indica un errore nell'aggigunta di una attività
     */
    private val _erroreAggiungiAttivita = MutableLiveData<String?>()
    val erroreAggiungiAttivita: LiveData<String?> = _erroreAggiungiAttivita

    /**
     * Indica un errore nella modifica di una attività
     */
    private val _erroreEditAttivita = MutableLiveData<String?>()
    val erroreEditAttivita: LiveData<String?> = _erroreEditAttivita

    /**
     * Indica se la modifica di un'attività è riuscita
     */
    private val _editAttivitaRiuscito = MutableLiveData(false)
    val editAttivitaRiuscito: LiveData<Boolean> get() = _editAttivitaRiuscito


    /**
     * Imposta un messaggio di errore per l'aggiunta di un'attività.
     *
     * @param message Il messaggio di errore.
     */
    private fun setErroreAggiungiTask(message: String) {
        _erroreAggiungiAttivita.value = message
    }

    /**
     * Imposta un messaggio di errore per la modifica di un'attività.
     *
     * @param message Il messaggio di errore.
     */
    private fun setErroreEditTask(message: String) {
        _erroreEditAttivita.value = message
    }

    /**
     * Resetta il messaggio di errore per l'aggiunta di un'attività.
     */
    fun resetErroreAggiungiTask() {
        _erroreAggiungiAttivita.value = null
    }

    /**
     * Resetta il messaggio di errore per la modifica di un'attività.
     */
    fun resetErroreEditTask() {
        _erroreEditAttivita.value = null
    }

    /**
     * Recupera le attività di un utente per un progetto specifico.
     *
     * @param idProg L'ID del progetto.
     * @param utenteId L'ID dell'utente.
     */
    fun getTodoUtente(idProg: String, utenteId: String) {
        Log.d("ViewModel", "Chiamato getTodoUtente con idProg: $idProg e utenteId: $utenteId")
        viewModelScope.launch {
            try {
                val result = repositoryLeMieAttivita.getTodoByUtente(idProg, utenteId)
                Log.d("ViewModel", "Attività recuperate: $result")
                leMieAttivitaPerUtente = result
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ViewModel", "Errore recupero attività: ${e.message}")
            }
        }
    }

    /**
     * Imposta l'URI del file.
     *
     * @param uri L'URI del file.
     */
    fun setFileUri(uri: Uri?) {
        _fileUri.value = uri
    }


    /**
     * Legge il contenuto di un file dato il suo URI.
     *
     * @param context Il contesto dell'applicazione.
     * @param uri L'URI del file.
     * @return Il contenuto del file.
     */
    suspend fun readFileContent(context: Context, uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            context.contentResolver.openInputStream(uri)?.bufferedReader().use { reader ->
                reader?.readText()
            }
        }
    }

    /**
     * Carica un file e aggiorna un'attività con i dettagli forniti.
     *
     * @param id L'ID dell'attività da aggiornare.
     * @param titolo Il nuovo titolo dell'attività.
     * @param descrizione La nuova descrizione dell'attività.
     * @param dataScad La nuova data di scadenza dell'attività.
     * @param priorita La nuova priorità dell'attività.
     * @param sezione La sezione dell'attività.
     */
    fun uploadFileAndSaveTodo(
        id: String,
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorita: Priorita,
        sezione: Int,
        dataScadenzaProgetto: Date
    ) {
        _editAttivitaRiuscito.value = false

        // Validazione dell'input
        if (!validateInput(titolo, dataScad, dataScadenzaProgetto)) {
            return
        }

        viewModelScope.launch {
            val todo = getTodoById(id)
            if (todo == null) {
                setErroreEditTask("Errore: Attività non trovata.")
                return@launch
            }

            val fileUri = _fileUri.value
            val downloadUrl = if (fileUri != null) {
                try {
                    uploadFile(fileUri)
                } catch (e: Exception) {
                    setErroreEditTask("Errore durante il caricamento del file: ${e.message}")
                    return@launch
                }
            } else {
                todo.fileUri  // Mantieni l'URL esistente se non c'è un nuovo file
            }

            try {
                // Aggiorna l'attività solo se tutte le operazioni precedenti sono andate a buon fine
                updateTodo(
                    id = id,
                    titolo = titolo,
                    descrizione = descrizione,
                    dataScad = dataScad,
                    priorita = priorita,
                    sezione = sezione,
                    progetto = todo.progetto,
                    utenti = todo.utenti,
                    fileUri = downloadUrl
                )
                _editAttivitaRiuscito.value = true
                resetErroreEditTask()  // Reset dell'errore in caso di successo
            } catch (e: Exception) {
                _editAttivitaRiuscito.value = false
                setErroreEditTask("Errore durante l'aggiornamento dell'attività: ${e.message}")
            }
        }
    }

    /**
     * Valida i campi di input per l'aggiornamento di un'attività.
     *
     * Questa funzione verifica che il titolo dell'attività non sia vuoto, che la data di scadenza
     * non sia antecedente alla data odierna e che la data di scadenza non superi la data di scadenza
     * del progetto associato.
     *
     * @param titolo Il titolo dell'attività. Non può essere vuoto.
     * @param dataScad La data di scadenza dell'attività. Deve essere una data valida e non precedente a quella odierna.
     * @param dataScadenzaProgetto La data di scadenza del progetto associato all'attività. La data di scadenza dell'attività non può essere successiva a questa data.
     * @return `true` se tutti i campi di input sono validi, `false` altrimenti.
     */
    private fun validateInput(titolo: String, dataScad: Date, dataScadenzaProgetto: Date): Boolean {
        return when {
            titolo.isBlank() -> {
                setErroreEditTask("Errore: Il titolo dell'attività non può essere vuoto.")
                false
            }
            isDateBeforeToday(dataScad) -> {
                setErroreEditTask("Errore: La data di scadenza dell'attività non può essere precedente alla data odierna.")
                false
            }
            dataScad.after(dataScadenzaProgetto) -> {
                setErroreEditTask("Errore: La data di scadenza dell'attività non può essere successiva alla data di scadenza del progetto.")
                false
            }
            else -> true
        }
    }


    fun resetEditAttivitaRiuscito(){
        _editAttivitaRiuscito.value = false
    }


    /**
     * Carica un file su Firebase Storage e restituisce l'URL di download.
     *
     * @param uri L'URI del file da caricare.
     * @return L'URL di download del file caricato.
     * @throws Exception Se si verifica un errore durante il caricamento del file.
     */
    private suspend fun uploadFile(uri: Uri): String {
        return withContext(Dispatchers.IO) {
            val storageReference = FirebaseStorage.getInstance().reference.child("files/${UUID.randomUUID()}")
            val uploadTask = storageReference.putFile(uri)
            uploadTask.await()  // Attendi il completamento dell'upload
            storageReference.downloadUrl.await().toString()  // Ottieni l'URL di download
        }
    }



    /**
     * Aggiorna un'attività esistente.
     *
     * @param id L'ID dell'attività.
     * @param titolo Il titolo dell'attività.
     * @param descrizione La descrizione dell'attività.
     * @param dataScad La data di scadenza dell'attività.
     * @param priorita La priorità dell'attività.
     * @param sezione La sezione dell'attività.
     * @param progetto L'ID del progetto associato all'attività.
     * @param utenti La lista degli utenti associati all'attività.
     * @param fileUri L'URI del file associato all'attività.
     */
    fun updateTodo(
        id: String,
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorita: Priorita,
        sezione: Int,
        progetto: String,
        utenti: List<String>,
        fileUri: String?
    ) {
        if (titolo.isBlank()) {
            setErroreEditTask("MODIFICA RIFIUTATA!!!: Il titolo non può essere omesso.")
            Log.d("Errore Edit", "Errore Edit Task: " + "${erroreEditAttivita.value}")

            return
        }
        if (isDateBeforeToday(dataScad)) {
            setErroreEditTask("MODIFICA RIFIUTATA!!!: La data di scadenza non può essere precedente alla data di creazione della Task.")
            return
        }
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.updateTodo(id, titolo, descrizione, dataScad, priorita,progetto,utenti,fileUri)
                if (sezione == 1) getNonCompletedTodoByProject(progetto)  else if (sezione == 0) getTodoCompletateByProject(progetto) else getTodoUtente(progetto, utenteId.toString())

            } catch (e: Exception) {
                Log.e("LeMieAttivitaViewModel", "Errore nell'aggiornamento di una task", e)
            }
        }
    }

    /**
     * Aggiorna il conteggio delle attività totali per un progetto.
     *
     * @param id L'ID del progetto.
     */
    private fun updateTaskTotali(id: String) {
        viewModelScope.launch {
            val completedTasks = repositoryLeMieAttivita.countAllTodo(id)
            _attivitaTotali.value = completedTasks
        }
    }

    /**
     * Aggiorna il conteggio delle attività completate per un progetto.
     *
     * @param id L'ID del progetto.
     */
    private fun updateTaskCompletate(id: String) {
        viewModelScope.launch {
            val completedTasks = repositoryLeMieAttivita.countCompletedTodo(id)
            _attivitaCompletate.value = completedTasks
        }
    }

    /**
     * Aggiorna la progressione delle attività completate per un progetto.
     *
     * @param id L'ID del progetto.
     */
    private fun updateProgress(id: String) {
        viewModelScope.launch {
            val completedTasks = repositoryLeMieAttivita.countCompletedTodo(id)
            _progressione.value =
                if (repositoryLeMieAttivita.countAllTodo(id) > 0) completedTasks.toFloat() / repositoryLeMieAttivita.countAllTodo(id) else 0.0f
        }
    }

    /**
     * Recupera tutte le attività per un progetto.
     *
     * @param progetto L'ID del progetto.
     * @return La lista delle attività.
     */
    suspend fun getAllTodoByProject(progetto: String): List<LeMieAttivita> {
        return suspendCoroutine { continuation ->
            viewModelScope.launch {
                var appoggio: List<LeMieAttivita> = emptyList()
                var tentativi = 0
                val maxTentativi = 15

                while (appoggio.isEmpty() && tentativi <= maxTentativi) {
                    getTodoCompletateByProject(progetto)
                    getNonCompletedTodoByProject(progetto)
                    appoggio = (leMieAttivitaCompletate + leMieAttivitaNonCompletate)
                    delay(200)
                    tentativi++
                }
                continuation.resume(appoggio)
                updateProgress(progetto)
                updateTaskCompletate(progetto)
                updateTaskTotali(progetto)
            }
        }
    }

    /**
     * Recupera le attività non completate per un progetto.
     *
     * @param progetto L'ID del progetto.
     */
    fun getNonCompletedTodoByProject(progetto: String) {
        viewModelScope.launch {
            var allTodo = repositoryLeMieAttivita.getNotCompletedTodo()
            var tentativi = 0
            val maxTentativi = 6
            while (allTodo.isEmpty() && tentativi < maxTentativi) {
                _isLoading.value = true
                delay(500) // Attendiamo mezzo secondo tra ogni tentativo
                allTodo = repositoryLeMieAttivita.getNotCompletedTodo()
                tentativi++
            }
            _isLoading.value = false
            leMieAttivitaNonCompletate = allTodo.filter { it.progetto == progetto && !it.completato }
            updateProgress(progetto)
            updateTaskCompletate(progetto)
            updateTaskTotali(progetto)

        }
    }

    /**
     * Recupera le attività completate per un progetto.
     *
     * @param progetto L'ID del progetto.
     */
    fun getTodoCompletateByProject(progetto: String) {
        viewModelScope.launch {
            var tentativi = 0
            val maxTentativi = 6
            _isLoading.value = true
            try {
                var allTodo = repositoryLeMieAttivita.getAllTodoCompletate()
                while (allTodo.isEmpty() && tentativi < maxTentativi){
                    _isLoading.value = true
                    delay(500) // Attendiamo mezzo secondo tra ogni tentativo
                    allTodo = repositoryLeMieAttivita.getAllTodoCompletate()
                    tentativi++
                }
                leMieAttivitaCompletate = allTodo.filter { it.progetto == progetto && it.completato}
                updateProgress(progetto)
                updateTaskCompletate(progetto)
                updateTaskTotali(progetto)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }


    /**
     * Recupera le attività completate per un progetto con callback.
     *
     * @param progetto L'ID del progetto.
     * @param callback La funzione di callback da eseguire.
     */
    fun getTodoCompletateByProject2(progetto: String, callback: (List<LeMieAttivita>) -> Unit) {
        viewModelScope.launch {
            var tentativi = 0
            val maxTentativi = 6
            _isLoading.value = true
            var completateAttivita: List<LeMieAttivita> = emptyList()
            try {
                var allTodo = repositoryLeMieAttivita.getAllTodoCompletate()
                while (allTodo.isEmpty() && tentativi < maxTentativi) {
                    _isLoading.value = true
                    delay(500) // Attendiamo mezzo secondo tra ogni tentativo
                    allTodo = repositoryLeMieAttivita.getAllTodoCompletate()
                    tentativi++
                }
                completateAttivita = allTodo.filter { it.progetto == progetto && it.completato }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
                callback(completateAttivita)
                updateProgress(progetto)
                updateTaskCompletate(progetto)
                updateTaskTotali(progetto)
            }
        }
    }


    /**
     * Verifica se una data è antecedente a oggi.
     *
     * @param date La data da verificare.
     * @return true se la data è antecedente a oggi, altrimenti false.
     */
    private fun isDateBeforeToday(date: Date): Boolean {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val dateToCompare = cal.time

        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)

        return dateToCompare.before(today.time)
    }

    /**
     * Aggiunge una nuova attività.
     *
     * @param titolo Il titolo dell'attività.
     * @param descrizione La descrizione dell'attività.
     * @param dataScad La data di scadenza dell'attività.
     * @param priorita La priorità dell'attività.
     * @param proprietario L'ID del proprietario dell'attività.
     * @param progetto L'ID del progetto associato all'attività.
     * @param sezione La sezione dell'attività.
     */
    fun addTodo(
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorita: Priorita,
        proprietario: String,
        progetto: String,
        sezione: Int,
        dataScadenzaProgetto: Date
    ) {
        if (titolo.isBlank()) {
            setErroreAggiungiTask("AGGIUNGI RIFIUTATO!!!: Il titolo non può essere omesso.")
            return
        }
        if (isDateBeforeToday(dataScad)) {
            setErroreAggiungiTask("AGGIUNGI RIFIUTATO!!!: La data di scadenza non può essere precedente alla data di creazione della Task.")
            return
        }
        if (dataScad.after(dataScadenzaProgetto)) {
            setErroreAggiungiTask("AGGIUNGI RIFIUTATO!!!: La data di scadenza non può essere successiva alla data di scadenza del progetto.")
            return
        }

        viewModelScope.launch {
            repositoryLeMieAttivita.addTodo(
                titolo,
                descrizione,
                dataScad,
                priorita,
                proprietario,
                progetto
            )
            if(sezione == 1) getNonCompletedTodoByProject(progetto) else if (sezione == 2) getTodoUtente(progetto, utenteId.toString())
        }
    }

    /**
     * Elimina un'attività.
     *
     * @param idTask L'ID dell'attività.
     * @param sezione La sezione dell'attività.
     * @param progetto L'ID del progetto associato all'attività.
     */
    fun deleteTodo(idTask: String, sezione: Int, progetto: String) {
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.deleteTodo(idTask)
                if (sezione == 0) getTodoCompletateByProject(progetto) else if ( sezione == 1)getNonCompletedTodoByProject(progetto) else getTodoUtente(progetto, utenteId.toString())
            } catch (e: Exception) {
                Log.e("LeMieAttivitaViewModel", "Errore nell'eliminazione della todo con id: $idTask", e)
            }
        }
    }

    /**
     * Completa o annulla il completamento di un'attività.
     *
     * @param idTask L'ID dell'attività.
     * @param completato Lo stato di completamento dell'attività.
     * @param sezione La sezione dell'attività.
     * @param progetto L'ID del progetto associato all'attività.
     */
    fun completeTodo(idTask: String, completato: Boolean, sezione: Int, progetto: String) {
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.completeTodo(idTask, completato)
                if (sezione == 0) getTodoCompletateByProject(progetto) else if (sezione==1) getNonCompletedTodoByProject(progetto) else getTodoUtente(progetto, utenteId.toString())
            } catch (e: Exception) {
                Log.e("LeMieAttivitaViewModel", "Errore nel completamento della todo con id: $idTask", e)
            }
        }
    }

    /**
     * Aggiunge una persona a un'attività.
     *
     * @param idTask L'ID dell'attività.
     * @param idPersona L'ID della persona da aggiungere.
     */
    fun aggiungiPersona(idTask: String, idPersona: String) {
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.addUserToTodo(idTask, idPersona)

            } catch (e: Exception) {
                Log.e("LeMieAttivitaViewModel", "Errore nell'aggiungere una persiona con id: $idPersona alla todo con id: $idTask", e)
            }
        }
    }

    /**
     * Rimuove una persona da un'attività.
     *
     * @param idTask L'ID dell'attività.
     * @param idPersona L'ID della persona da rimuovere.
     */
    fun rimuoviPersona(idTask: String, idPersona: String) {
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.removeUserFromTodo(idTask, idPersona)
            } catch (e: Exception) {
                Log.e("LeMieAttivitaViewModel", "Errore nel rimuovere una persona con id: $idPersona dalla todo con id: $idTask", e)
            }
        }
    }

    /**
     * Recupera un'attività dato il suo ID.
     *
     * @param idTask L'ID dell'attività.
     * @return L'attività recuperata.
     */
    suspend fun getTodoById(idTask: String): LeMieAttivita? {
        return try {
            repositoryLeMieAttivita.getTodoById(idTask)
        } catch (e: Exception) {
            Log.e("LeMieAttivitaViewModel", "Errore nel reperire la todo con id: $idTask", e)
            null
        }
    }
}
