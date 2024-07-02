package com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

    private fun getAllTodo() {
        viewModelScope.launch {
            leMieAttività = repository.getAllTodo()
        }
    }

    fun addTodo(titolo: String, descrizione: String, dataScad: Date, priorità: Priorità) {
        viewModelScope.launch {
            repository.addTodo(titolo, descrizione, dataScad, priorità)
            getAllTodo()  // Refresh the list after adding a new item
        }
    }

    fun deleteTodo(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteTodo(id)
                getAllTodo()  // Aggiorna la lista dopo l'eliminazione
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
                getAllTodo()  // Aggiorna la lista dopo l'aggiornamento
            } catch (e: Exception) {
                // Gestisci l'errore se necessario
            }
        }
    }
}



