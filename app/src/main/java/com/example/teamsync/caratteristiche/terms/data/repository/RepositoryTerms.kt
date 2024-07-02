package com.example.teamsync.caratteristiche.terms.data.repository

import com.example.teamsync.caratteristiche.terms.data.model.terms
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

class RepositoryTerms {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    suspend fun getAllTerms(): List<terms> {
        return try {
            val risultato = firestore.collection("terms&policies")
                .orderBy(FieldPath.documentId())
                .get()
                .await()
            risultato.documents.mapNotNull { it.toObject(terms::class.java) }
        } catch (e: Exception) {
            // Log error
            emptyList()
        }
    }

    suspend fun get_last_update(): Date? {
        val result = firestore.collection("terms&policies")
            .orderBy("data", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        return result.documents.firstOrNull()?.getTimestamp("data")?.toDate()
    }
}
