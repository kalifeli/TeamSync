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
                        completato: Boolean,
                        utenti : String,
                        progetto: String
    ) {
        val leMieAttivita = LeMieAttivita(
            titolo = titolo,
            descrizione = descrizione,
            dataScadenza = dataScad,
            priorita = priorita,
            completato = false,
            progetto = progetto,
            utenti = listOf(utenti),
        )
        database.collection("Todo").add(leMieAttivita).await()
    }

    suspend fun getAllTodo(progetto: String): List<LeMieAttivita> {
        val snapshot = database.collection("Todo")
            .whereEqualTo("completato", false)
            .orderBy("dataScadenza") // Ordina per data di scadenza
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(LeMieAttivita::class.java) }
    }


    suspend fun getAllTodoCompletate(progetto: String): List<LeMieAttivita> {
        val snapshot = database.collection("Todo")
            .whereEqualTo("completato", true)
            .whereEqualTo("progetto", progetto)
            .orderBy("dataScadenza")
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
        progetto: String,
        utenti : List<String>,
        fileUri: String?

    ) {
        try {
            val updatedTodo = LeMieAttivita(
                id = id,
                titolo = titolo,
                descrizione = descrizione,
                dataScadenza = dataScad,
                priorita = priorita,
                progetto = progetto,
                utenti = utenti,
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

    suspend fun addUserToTodo(id: String, newUser: String) {
        try {
            val document = database.collection("Todo").document(id).get().await()
            val leMieAttivita = document.toObject(LeMieAttivita::class.java)
            if (leMieAttivita != null) {
                val updatedUsers = leMieAttivita.utenti.toMutableList()
                updatedUsers.add(newUser)
                database.collection("Todo")
                    .document(id)
                    .update("utenti", updatedUsers)
                    .await()
            } else {
                throw Exception("Todo non trovato")
            }
        } catch (e: Exception) {
            // Gestisci l'errore se necessario
            throw Exception("Errore durante l'aggiornamento degli utenti del Todo: ${e.message}")
        }
    }


    suspend fun removeUserFromTodo(id: String, userToRemove: String) {
        try {
            val document = database.collection("Todo").document(id).get().await()
            val leMieAttivita = document.toObject(LeMieAttivita::class.java)
            if (leMieAttivita != null) {
                val updatedUsers = leMieAttivita.utenti.toMutableList()
                if (updatedUsers.remove(userToRemove)) {
                    database.collection("Todo")
                        .document(id)
                        .update("utenti", updatedUsers)
                        .await()
                } else {
                    throw Exception("Utente non trovato nella lista")
                }
            } else {
                throw Exception("Todo non trovato")
            }
        } catch (e: Exception) {
            // Gestisci l'errore se necessario
            throw Exception("Errore durante la rimozione dell'utente dal Todo: ${e.message}")
        }
    }


    suspend fun getTodoById(id: String): LeMieAttivita? {
        return try {
            val document = database.collection("Todo").document(id).get().await()
            document.toObject(LeMieAttivita::class.java)
        } catch (e: Exception) {
            // Gestisci l'errore se necessario
            throw Exception("Errore durante il recupero del Todo: ${e.message}")
        }
    }


    suspend fun countCompletedTodo(progetto: String): Int {
        val snapshot = database.collection("Todo")
            .whereEqualTo("progetto", progetto)
            .whereEqualTo("completato", true)
            .get()
            .await()
        return snapshot.size()
    }
    suspend fun countAllTodo(progetto: String): Int {
        val snapshot = database.collection("Todo")
            .whereEqualTo("progetto", progetto)
            .get()
            .await()
        return snapshot.size()
    }

}

