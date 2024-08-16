package com.example.teamsync.caratteristiche.impostazioni.data.repository

import com.example.teamsync.caratteristiche.impostazioni.data.model.Terms
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

/**
 * Repository per la gestione delle operazioni relative ai Termini e Condizioni (Terms) su Firebase Firestore.
 */
class RepositoryTerms {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Recupera tutti i termini e condizioni dalla collezione "terms&policies" di Firestore.
     *
     * @return Una lista di oggetti [Terms] contenente tutti i termini e condizioni disponibili.
     *         Se si verifica un errore durante il recupero, viene restituita una lista vuota.
     * @throws Exception se si verifica un errore durante il recupero dei Terms.
     */
    suspend fun getAllTerms(): List<Terms> {
        return try {
            // Recupera tutti i documenti dalla collezione "terms&policies" ordinati per ID del documento
            val risultato = firestore.collection("terms&policies")
                .orderBy(FieldPath.documentId())
                .get()
                .await()

            // Mappa i documenti a oggetti Terms e restituisce la lista
            risultato.documents.mapNotNull { it.toObject(Terms::class.java) }
        } catch (e: Exception) {
            // In caso di errore, restituisce una lista vuota
            emptyList()
        }
    }

    /**
     * Recupera la data dell'ultimo aggiornamento dei termini e condizioni.
     *
     * @return Un oggetto [Date] rappresentante la data dell'ultimo aggiornamento, oppure `null` se non è disponibile.
     * @throws Exception se si verifica un errore durante il recupero della data.
     */
    suspend fun getLastUpdate(): Date? {
        return try {
            // Recupera il documento più recente dalla collezione "terms&policies" ordinato per data
            val result = firestore.collection("terms&policies")
                .orderBy("data", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            // Restituisce la data del primo documento, oppure null se non disponibile
            result.documents.firstOrNull()?.getTimestamp("data")?.toDate()
        } catch (e: Exception) {
            // In caso di errore, restituisce null
            null
        }
    }
}
