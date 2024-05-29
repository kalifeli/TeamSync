package com.example.teamsync.caratteristiche.login.data.repository

import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RepositoryUtente {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun signIn(email: String, password: String): FirebaseUser?{
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user
    }

    suspend fun signUp(matricola: String, nome: String, cognome: String, dataNascita: String, email: String, password: String): FirebaseUser?{
        val result = auth.createUserWithEmailAndPassword(email, password).await()

        val utente = result.user ?: return null

        val profiloUtente = ProfiloUtente(utente.uid, matricola, nome, cognome, dataNascita, email)
        firestore.collection("utenti").document(utente.uid).set(profiloUtente)

        return utente
    }
    suspend fun getUtenteAttuale(): FirebaseUser?{
        val utenteCorrente = auth.currentUser
        return utenteCorrente
    }
    suspend fun setPreferenzeLinguaEmail(lingua: String){
        auth.setLanguageCode(lingua)
    }


}