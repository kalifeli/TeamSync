package com.example.teamsync.caratteristiche.LeMieAttivita.data.model

import com.example.teamsync.data.models.Priorità
import java.util.Date



object LeMieAttivitaManager {
    private val leMieAttivitaList = mutableListOf<LeMieAttivita>()

    fun getAllTodo() : List<LeMieAttivita>{
        return leMieAttivitaList
    }
    fun addTodo(titolo: String, descrizione: String, dataScad : Date, priorita : Priorità){
        leMieAttivitaList.add(LeMieAttivita(System.currentTimeMillis().toInt(), titolo,descrizione,dataScad, priorita))
    }
    fun deleteTodo(id : Int){
        val iterator = leMieAttivitaList.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().taskId == id) {
                iterator.remove()
            }
        }
    }
}
