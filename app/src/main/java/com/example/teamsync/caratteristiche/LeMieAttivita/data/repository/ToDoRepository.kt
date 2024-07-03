package com.example.teamsync.caratteristiche.LeMieAttivita.data.repository

import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.data.models.Priorità
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date
import com.google.firebase.Timestamp



class ToDoRepository {
    private val database = FirebaseFirestore.getInstance()

    suspend fun addTodo(titolo: String,
                        descrizione: String,
                        dataScad: Date,
                        priorità: Priorità,
                        completato: Boolean) {
        val leMieAttivita = LeMieAttivita(
            titolo = titolo,
            descrizione = descrizione,
            dataScadenza = dataScad,
            priorita = priorità,
            completato = false,
        )
        database.collection("Todo").add(leMieAttivita).await()
    }



    suspend fun getAllTodo(): List<LeMieAttivita> {
        val snapshot = database.collection("Todo")
            .whereEqualTo("completato", false)
            .orderBy("dataScadenza") // Ordina per data di scadenza
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(LeMieAttivita::class.java) }
    }




    suspend fun getAllTodoCompletate(): List<LeMieAttivita> {
        val snapshot = database.collection("Todo")
            .whereEqualTo("completato", true)
            .orderBy("dataScadenza") // Ordina per data di scadenza
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(LeMieAttivita::class.java) }
    }


    suspend fun deleteTodo(id: String) {
        try {
            database.collection("Todo").document(id).delete().await()
        } catch (e: Exception) {
            // Gestisci l'errore se necessario
            throw Exception("Errore durante l'eliminazione del Todo: ${e.message}")
        }
    }


    suspend fun updateTodo(
        id: String,
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorità: Priorità
    ) {
        try {
            val updatedTodo = LeMieAttivita(
                id = id,
                titolo = titolo,
                descrizione = descrizione,
                dataScadenza = dataScad,
                priorita = priorità
            )
            database.collection("Todo").document(id).set(updatedTodo).await()
        } catch (e: Exception) {
            // Gestisci l'errore se necessario
            throw Exception("Errore durante l'aggiornamento del Todo: ${e.message}")
        }
    }


    suspend fun completeTodo(id: String, completato: Boolean){
        database.collection("Todo")
            .document(id)
            .update("completato", !completato)
            .await()
    }
}