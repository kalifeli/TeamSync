package com.example.teamsync.caratteristiche.LeMieAttivita.data.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivitaManager
import com.example.teamsync.data.models.Priorità
import java.util.Date

class LeMieAttivitaViewModel : ViewModel(){
    private var _leMieAttivita = MutableLiveData<List<LeMieAttivita>>()
    val todolist : LiveData<List<LeMieAttivita>> = _leMieAttivita

    fun getAllTodo(){
        _leMieAttivita.value = LeMieAttivitaManager.getAllTodo()
    }
    fun addTodo(titolo: String, descrizione: String, dataScad : Date, priorita : Priorità){
        LeMieAttivitaManager.addTodo(titolo, descrizione,dataScad,priorita)
    }
    fun deleteTodo(id : Int){
        LeMieAttivitaManager.deleteTodo(id)
    }
}
