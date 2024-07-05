package com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel

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

class LeMieAttivitaViewModel(private val repository: ToDoRepository) : ViewModel() {
    var leMieAttivita by mutableStateOf<List<LeMieAttivita>>(emptyList())
        private set

    init {
        getAllTodo()
    }

    fun getAllTodo() {
        viewModelScope.launch {
            leMieAttivita = repository.getAllTodo()
        }
    }

    fun getAllTodoCompletate() {
        viewModelScope.launch {
            leMieAttivita = repository.getAllTodoCompletate()
        }
    }


    fun addTodo(titolo: String, descrizione: String, dataScad: Date, priorita: Priorità, completato: Boolean) {
        viewModelScope.launch {
            repository.addTodo(titolo, descrizione, dataScad, priorita, completato = false)
            getAllTodo()
        }
    }

    fun deleteTodo(id: String, sezione: Int) {
        viewModelScope.launch {
            try {
                repository.deleteTodo(id)
                if (sezione == 0) getAllTodoCompletate() else getAllTodo()
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
        sezione: Int
    ) {
        viewModelScope.launch {
            try {
                repository.updateTodo(id, titolo, descrizione, dataScad, priorita)
                if (sezione == 0) getAllTodoCompletate() else getAllTodo()
            } catch (e: Exception) {
                // Gestisci l'errore se necessario
            }
        }
    }
    fun completeTodo(id: String, completato: Boolean, sezione: Int){
        viewModelScope.launch {
            try {
                repository.completeTodo(id, completato)
                if (sezione == 0) getAllTodoCompletate() else getAllTodo()
            }catch (e: Exception){
                //gestire errore
            }
        }
    }
}



