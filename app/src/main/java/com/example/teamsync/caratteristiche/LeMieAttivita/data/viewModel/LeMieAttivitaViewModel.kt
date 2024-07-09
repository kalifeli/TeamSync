package com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.LeMieAttivita.data.repository.ToDoRepository
import com.example.teamsync.data.models.Priorità
import kotlinx.coroutines.launch
import java.util.Date
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID


class LeMieAttivitaViewModel() : ViewModel() {
    val repositoryLeMieAttivita = ToDoRepository()
    var leMieAttivita by mutableStateOf<List<LeMieAttivita>>(emptyList())
        private set
    val _progressione = MutableStateFlow(0.0f)
    val progressione: StateFlow<Float> = _progressione // Esponi solo lo StateFlow

    val _taskCompletate = MutableStateFlow(0)
    val taskCompletate: StateFlow<Int> = _taskCompletate

    val _taskNonCompletate = MutableStateFlow(0)
    val taskNonCompletate: StateFlow<Int> = _taskNonCompletate

    private val _fileUri = MutableLiveData<Uri?>()
    val fileUri: LiveData<Uri?> get() = _fileUri

    private val _uploadResult = MutableLiveData<String?>()
    val uploadResult: LiveData<String?> get() = _uploadResult

    init {
        getAllTodo()
    }

    fun setFileUri(uri: Uri?) {
        _fileUri.value = uri
    }

    fun uploadFileAndSaveTodo(
        id: String,
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorita: Priorità,
        sezione: Int
    ) {
        viewModelScope.launch {
            val uri = _fileUri.value ?: return@launch
            val storageReference = FirebaseStorage.getInstance().reference.child("files/${UUID.randomUUID()}")
            try {
                val uploadTask = storageReference.putFile(uri)
                uploadTask.await()  // Attendi che l'upload sia completato
                val downloadUrl = storageReference.downloadUrl.await()  // Ottieni l'URL di download
                _uploadResult.value = downloadUrl.toString()

                // Chiama la funzione updateTodo con tutti i parametri richiesti
                updateTodo(id, titolo, descrizione, dataScad, priorita, downloadUrl.toString(), sezione)
            } catch (e: Exception) {
                _uploadResult.value = null  // In caso di errore, imposta l'URL come null
            }
        }
    }

    fun updateTaskNonCompletate() {
        viewModelScope.launch {
            val completedTasks = repositoryLeMieAttivita.countAllTodo()
            _taskNonCompletate.value = completedTasks
        }
    }

    fun updateTaskCompletate() {
        viewModelScope.launch {
            val completedTasks = repositoryLeMieAttivita.countCompletedTodo()
            _taskCompletate.value = completedTasks
        }
    }

    fun updateProgress() {
        viewModelScope.launch {
            val completedTasks = repositoryLeMieAttivita.countCompletedTodo()
            _progressione.value =
                if (repositoryLeMieAttivita.countAllTodo() > 0) completedTasks.toFloat() / repositoryLeMieAttivita.countAllTodo() else 0.0f
        }
    }

    fun getAllTodo() {
        viewModelScope.launch {
            leMieAttivita = repositoryLeMieAttivita.getAllTodo()
            updateProgress()
            updateTaskCompletate()
            updateTaskNonCompletate()
        }
    }

    fun getAllTodoCompletate() {
        viewModelScope.launch {
            leMieAttivita = repositoryLeMieAttivita.getAllTodoCompletate()
            updateProgress()
            updateTaskCompletate()
            updateTaskNonCompletate()
        }
    }


    fun addTodo(titolo: String, descrizione: String, dataScad: Date, priorita: Priorità, completato: Boolean) {
        viewModelScope.launch {
            repositoryLeMieAttivita.addTodo(titolo, descrizione, dataScad, priorita, completato = false)
            getAllTodo()
            updateProgress()
        }
    }

    fun deleteTodo(id: String, sezione: Int) {
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.deleteTodo(id)
                if (sezione == 0) getAllTodoCompletate() else getAllTodo()
                updateProgress()
            } catch (e: Exception) {
                // Gestisci l'errore se necessario
            }
        }
    }
    fun updateTodo(
        id: String,
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorita: Priorità,
        fileUri: String?,
        sezione: Int
    ) {
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.updateTodo(id, titolo, descrizione, dataScad, priorita, fileUri)
                if (sezione == 0) getAllTodoCompletate() else getAllTodo()
            } catch (e: Exception) {
                // Gestisci l'errore se necessario
            }
        }
    }
    fun completeTodo(id: String, completato: Boolean, sezione: Int){
        viewModelScope.launch {
            try {
                repositoryLeMieAttivita.completeTodo(id, completato)
                if (sezione == 0) getAllTodoCompletate() else getAllTodo()
                updateProgress()
            }catch (e: Exception){
                //gestire errore
            }
        }
    }



}



