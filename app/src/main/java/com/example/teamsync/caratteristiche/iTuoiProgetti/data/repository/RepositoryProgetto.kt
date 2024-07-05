package com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository

import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RepositoryProgetto {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun getProgettiUtente(userId: String): List<Progetto> {
        return try {
            firestore.collection("progetti")
                .whereArrayContains("partecipanti", userId)
                .get()
                .await()
                .toObjects(Progetto::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun aggiungiProgetto(progetto: Progetto): String {
        return try {
            val documentRef = firestore.collection("progetti").add(progetto).await()
            documentRef.id
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun aggiungiPartecipante(progettoId: String?, userId: String) {
        try {
            val progettoRef = firestore.collection("progetti").document(progettoId ?: "")
            progettoRef.update("partecipanti", FieldValue.arrayUnion(userId)).await()
        } catch (e: Exception) {
            throw e
        }
    }

    fun getUtenteCorrente(): FirebaseUser? {
        return try {
            auth.currentUser
        }catch (e: Exception){
            throw  e
        }
    }

    fun logout(){
        auth.signOut()
    }
}

