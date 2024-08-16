package com.example.teamsync.caratteristiche.impostazioni.data.repository

import com.example.teamsync.caratteristiche.impostazioni.data.model.Faq
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Repository per la gestione delle operazioni di recupero delle FAQ da Firebase Firestore.
 */
class RepositoryFaq {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Recupera tutte le FAQ dalla collezione "Faq" di Firestore.
     *
     * @return Una lista di oggetti [Faq] contenente tutte le FAQ disponibili.
     *         Se si verifica un errore durante il recupero, viene restituita una lista vuota.
     * @throws Exception se si verifica un errore durante il recupero delle FAQ.
     */
    suspend fun getAllFaq(): List<Faq> {
        return try {
            // Recupera tutti i documenti dalla collezione "Faq"
            val risultato = firestore.collection("Faq")
                .get()
                .await()
            // Mappa i documenti a oggetti Faq e restituisce la lista
            risultato.documents.mapNotNull { it.toObject(Faq::class.java) }
        } catch (e: Exception) {
            // In caso di errore, restituisce una lista vuota
            emptyList()
        }
    }
}
