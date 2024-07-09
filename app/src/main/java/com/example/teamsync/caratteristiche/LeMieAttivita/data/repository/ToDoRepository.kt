package com.example.teamsync.caratteristiche.LeMieAttivita.data.repository

import android.util.Log
import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.data.models.Priorità
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date



class ToDoRepository {
    private val database = FirebaseFirestore.getInstance()


    suspend fun addTodo(titolo: String,
                        descrizione: String,
                        dataScad: Date,
                        priorita: Priorità,
                        completato: Boolean) {
        val leMieAttivita = LeMieAttivita(
            titolo = titolo,
            descrizione = descrizione,
            dataScadenza = dataScad,
            priorita = priorita,
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

    //conteggio per la progress bar
    suspend fun countCompletedTodo(): Int {
        val snapshot = database.collection("Todo")
            .whereEqualTo("completato", true) // Filtra per attività completate
            .get()
            .await()
        return snapshot.size()
    }
    suspend fun countAllTodo(): Int {
        val snapshot = database.collection("Todo")
            .get()
            .await()
        return snapshot.size()
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
        priorita: Priorità,
        fileUri: String?
    ) {
        try {
            val updatedTodo = LeMieAttivita(
                id = id,
                titolo = titolo,
                descrizione = descrizione,
                dataScadenza = dataScad,
                priorita = priorita,
                fileUri = fileUri
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