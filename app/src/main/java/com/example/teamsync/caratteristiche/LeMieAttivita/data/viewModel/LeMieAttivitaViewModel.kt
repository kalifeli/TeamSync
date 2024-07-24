package com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.LeMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.caratteristiche.Notifiche.data.repository.RepositoryNotifiche
import com.example.teamsync.data.models.Priorità
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream




class LeMieAttivitaViewModel() : ViewModel() {
    val repositoryLeMieAttivita = ToDoRepository()
    var leMieAttivita by mutableStateOf<List<LeMieAttivita>>(emptyList())
        private set
    val _progressione = MutableStateFlow(0.0f)
    val progressione: StateFlow<Float> = _progressione // Esponi solo lo StateFlow

    private val _attivitaCompletate = MutableStateFlow(0)
    val attivitaCompletate: StateFlow<Int> = _attivitaCompletate

    private val _attivitaTotali = MutableStateFlow(0)
    val attivitaTotali: StateFlow<Int> = _attivitaTotali

    private val _fileUri = MutableLiveData<Uri?>()
    val fileUri: LiveData<Uri?> get() = _fileUri

    private val _uploadResult = MutableLiveData<String?>()
    val uploadResult: LiveData<String?> get() = _uploadResult


    var isLoading = mutableStateOf(true)
        private set
    val repositoryNotifiche = RepositoryNotifiche()

    var leMieAttivitaNonCompletate by mutableStateOf<List<LeMieAttivita>>(emptyList())
    var leMieAttivitaCompletate by mutableStateOf<List<LeMieAttivita>>(emptyList())


    var erroreEditTask = mutableStateOf<String?>(null)
        private set

    var leMieAttivitaPerUtente by mutableStateOf<List<LeMieAttivita>>(emptyList())

    private val _isPermissionGranted = MutableLiveData<Boolean>()


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


    fun setFileUri(uri: Uri?) {
        _fileUri.value = uri
    }


    suspend fun readFileContent(context: Context, uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            context.contentResolver.openInputStream(uri)?.bufferedReader().use { reader ->
                reader?.readText()
            }
        }
    }

    fun uploadFileAndSaveTodo(
        id: String,
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorita: Priorità,
        sezione: Int
    ) {
        if (isDateBeforeToday(dataScad)) {
            erroreEditTask.value = "MODIFICA RIFIUTATA: La data di scadenza non può essere precedente alla data della Task."
            return
        }
        viewModelScope.launch {
            val todo = getTodoById(id)
            val uri = _fileUri.value ?: return@launch
            val storageReference = FirebaseStorage.getInstance().reference.child("files/${UUID.randomUUID()}")
            try {
                val uploadTask = storageReference.putFile(uri)
                uploadTask.await()  // Attendi che l'upload sia completato
                val downloadUrl = storageReference.downloadUrl.await()  // Ottieni l'URL di download
                _uploadResult.value = downloadUrl.toString()

                // Chiama la funzione updateTodo con tutti i parametri richiesti
                if (todo != null) {
                    updateTodo(id, titolo, descrizione, dataScad, priorita,sezione,todo.progetto,todo.utenti, downloadUrl.toString())
                }
            } catch (e: Exception) {
                _uploadResult.value = null  // In caso di errore, imposta l'URL come null
            }
        }
    }


    fun updateTodo(
        id: String,
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorita: Priorità,
        sezione: Int,
        progetto: String,
        utenti: List<String>,
        fileUri: String?
    ) {
        if (isDateBeforeToday(dataScad)) {
            erroreEditTask.value = "MODIFICA RIFIUTATA: La data di scadenza non può essere precedente alla data della Task..."
            return
        }
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.updateTodo(id, titolo, descrizione, dataScad, priorita,progetto,utenti,fileUri)
                if (sezione == 0) getTodoCompletateByProject(progetto) else getTodoByProject(progetto)

            } catch (e: Exception) {
                // Gestisci l'errore se necessario
            }
        }
    }

    fun resetErroreEditTask() {
        erroreEditTask.value = null
    }

    fun updateTaskTotali(id: String) {
        viewModelScope.launch {
            val completedTasks = repositoryLeMieAttivita.countAllTodo(id)
            _attivitaTotali.value = completedTasks
        }
    }

    fun updateTaskCompletate(id: String) {
        viewModelScope.launch {
            val completedTasks = repositoryLeMieAttivita.countCompletedTodo(id)
            _attivitaCompletate.value = completedTasks
        }
    }

    fun updateProgress(id: String) {
        viewModelScope.launch {
            val completedTasks = repositoryLeMieAttivita.countCompletedTodo(id)
            _progressione.value =
                if (repositoryLeMieAttivita.countAllTodo(id) > 0) completedTasks.toFloat() / repositoryLeMieAttivita.countAllTodo(id) else 0.0f
        }
    }


    suspend fun getAllTodo_BY_Project(progetto: String): List<LeMieAttivita> {
        return suspendCoroutine { continuation ->
            viewModelScope.launch {
                var appoggio: List<LeMieAttivita> = emptyList()
                var tentativi = 0
                val MAX_TENTATIVI = 15

                while (appoggio.isEmpty() && tentativi <= MAX_TENTATIVI) {
                    Log.d("Attività all'interno", "P: ${appoggio}")
                    getTodoCompletateByProject(progetto)
                    getTodoByProject(progetto)
                    appoggio = (leMieAttivitaCompletate + leMieAttivitaNonCompletate).filterNotNull()
                    delay(200)
                    tentativi++
                    Log.d("Attività all'interno finale", "zzzz: ${appoggio}")

                }
                continuation.resume(appoggio)
                updateProgress(progetto)
                updateTaskCompletate(progetto)
                updateTaskTotali(progetto)
            }
        }
    }

    fun getTodoByProject(progetto: String) {
        viewModelScope.launch {
            var allTodo = repositoryLeMieAttivita.getAllTodo()
            var tentativi = 0
            val MAX_TENTATIVI = 6
            while (allTodo.isEmpty() && tentativi < MAX_TENTATIVI) {
                isLoading.value = true
                delay(500) // Attendiamo mezzo secondo tra ogni tentativo
                allTodo = repositoryLeMieAttivita.getAllTodo()
                tentativi++
            }
            isLoading.value = false
            leMieAttivitaNonCompletate = allTodo.filter { it.progetto == progetto && !it.completato }
            updateProgress(progetto)
            updateTaskCompletate(progetto)
            updateTaskTotali(progetto)

        }
    }

    fun getTodoCompletateByProject(progetto: String) {
        viewModelScope.launch {
            var tentativi = 0
            val MAX_TENTATIVI = 6
            isLoading.value = true
            try {
                var allTodo = repositoryLeMieAttivita.getAllTodoCompletate()
                while (allTodo.isEmpty() && tentativi < MAX_TENTATIVI){
                    isLoading.value = true
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
                isLoading.value = false
            }
        }
    }

    fun getTodoByUtente(progetto: String, userId: String) {
        viewModelScope.launch {
            var tentativi = 0
            val MAX_TENTATIVI = 6
            isLoading.value = true
            try {
                var allTodo = repositoryLeMieAttivita.getAllTodoCompletate()
                while (allTodo.isEmpty() && tentativi < MAX_TENTATIVI) {
                    isLoading.value = true
                    delay(500) // Attendiamo mezzo secondo tra ogni tentativo
                    allTodo = repositoryLeMieAttivita.getAllTodoCompletate()
                    tentativi++
                }
                leMieAttivitaCompletate = allTodo.filter { it.progetto == progetto && it.utenti.contains(userId) }
                updateProgress(progetto)
                updateTaskCompletate(progetto)
                updateTaskTotali(progetto)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }



    fun getTodoCompletateByProject2(progetto: String, callback: (List<LeMieAttivita>) -> Unit) {
        viewModelScope.launch {
            var tentativi = 0
            val MAX_TENTATIVI = 6
            isLoading.value = true
            var completateAttivita: List<LeMieAttivita> = emptyList()
            try {
                var allTodo = repositoryLeMieAttivita.getAllTodoCompletate()
                while (allTodo.isEmpty() && tentativi < MAX_TENTATIVI) {
                    isLoading.value = true
                    delay(500) // Attendiamo mezzo secondo tra ogni tentativo
                    allTodo = repositoryLeMieAttivita.getAllTodoCompletate()
                    tentativi++
                }
                completateAttivita = allTodo.filter { it.progetto == progetto && it.completato }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
                callback(completateAttivita)
                updateProgress(progetto)
                updateTaskCompletate(progetto)
                updateTaskTotali(progetto)
            }
        }
    }


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

    private val _erroreAggiungiTask = MutableLiveData<String?>()
    val erroreAggiungiTask: LiveData<String?> = _erroreAggiungiTask

    fun setErroreAggiungiTask(message: String) {
        _erroreAggiungiTask.value = message
    }

    fun resetErroreAggiungiTask() {
        _erroreAggiungiTask.value = null
    }

    fun addTodo(
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorita: Priorità,
        completato: Boolean,
        proprietario: String,
        progetto : String
    ) {
        if (titolo.isBlank()) {
            setErroreAggiungiTask("AGGIUNGI RIFIUTATO!!!: Il titolo non può essere omesso.")
            Log.e(TAG, "Errore Aggiungi Task: " + "${erroreAggiungiTask.value}");
            return
        }
        if (isDateBeforeToday(dataScad)) {
            setErroreAggiungiTask("AGGIUNGI RIFIUTATO!!!: La data di scadenza non può essere precedente alla data di creazione della Task.")
            return
        }

        viewModelScope.launch {
            repositoryLeMieAttivita.addTodo(
                titolo,
                descrizione,
                dataScad,
                priorita,
                completato = false,
                proprietario,
                progetto
            )
            getTodoByProject(progetto)
        }
    }

    fun deleteTodo(id: String, sezione: Int, progetto: String) {
        viewModelScope.launch {
            try {

                repositoryLeMieAttivita.deleteTodo(id)
                if (sezione == 0) getTodoCompletateByProject(progetto) else getTodoByProject(progetto)
            } catch (e: Exception) {
                // Gestisci l'errore se necessario
            }
        }
    }


    fun completeTodo(id: String, completato: Boolean, sezione: Int, progetto: String) {
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.completeTodo(id, completato)
                if (sezione == 0) getTodoCompletateByProject(progetto) else getTodoByProject(progetto)
            } catch (e: Exception) {
                //gestire errore
            }
        }
    }

    fun aggiungi_persona(id_task: String, id_persona: String) {
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.addUserToTodo(id_task, id_persona)

            } catch (e: Exception) {
                //gestire errore
            }
        }
    }

    fun rimuovi_persona(id_task: String, id_persona: String) {
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.removeUserFromTodo(id_task, id_persona)



            } catch (e: Exception) {
                //gestire errore
            }
        }
    }


    suspend fun getTodoById(id_task: String): LeMieAttivita? {
        return try {
            repositoryLeMieAttivita.getTodoById(id_task)
        } catch (e: Exception) {
            null // Gestisci l'errore se necessario
        }
    }
}
