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
    var leMieAttività by mutableStateOf<List<LeMieAttivita>>(emptyList())
        private set

    init {
        getAllTodo()
    }

    fun getAllTodo() {
        viewModelScope.launch {
            leMieAttività = repository.getAllTodo()
        }
    }

    fun getAllTodoCompletate() {
        viewModelScope.launch {
            leMieAttività = repository.getAllTodoCompletate()
        }
    }


    fun addTodo(titolo: String, descrizione: String, dataScad: Date, priorità: Priorità, completato: Boolean) {
        viewModelScope.launch {
            repository.addTodo(titolo, descrizione, dataScad, priorità, completato = false)
            getAllTodo()
        }
    }

    fun deleteTodo(id: String, sezione: Int) {
        viewModelScope.launch {
            try {
                repository.deleteTodo(id)
                if (sezione == 0) getAllTodo() else getAllTodoCompletate()
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
        priorità: Priorità
    ) {
        viewModelScope.launch {
            try {
                repository.updateTodo(id, titolo, descrizione, dataScad, priorità)
            } catch (e: Exception) {
                // Gestisci l'errore se necessario
            }
        }
    }
    fun completeTodo(id: String, completato: Boolean){
        viewModelScope.launch {
            try {
                repository.completeTodo(id, completato)
                getAllTodo()
            }catch (e: Exception){
                //gestire errore
            }
        }
    }
}



