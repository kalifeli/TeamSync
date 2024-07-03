package com.example.teamsync.caratteristiche.terms.data.repository

import com.example.teamsync.caratteristiche.terms.data.model.Faq
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RepositoryFaq {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    suspend fun getAllFaq(): List<Faq> {
        return try {
            val risultato = firestore.collection("Faq")
                .get()
                .await()
            risultato.documents.mapNotNull { it.toObject(Faq::class.java) }
        } catch (e: Exception) {
            // Log error
            emptyList()
        }
    }


}
