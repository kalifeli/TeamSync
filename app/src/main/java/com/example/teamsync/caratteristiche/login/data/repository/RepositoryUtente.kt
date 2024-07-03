package com.example.teamsync.caratteristiche.login.data.repository

import android.net.Uri
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage

import kotlinx.coroutines.tasks.await
import java.util.UUID


class ImageUploader {


    fun uploadImageToFirebaseStorage(uri: Uri): Task<Uri> {
        val storageRef = Firebase.storage.reference
        val imagesRef = storageRef.child("images/${UUID.randomUUID()}")

        return imagesRef.putFile(uri)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                imagesRef.downloadUrl
            }
    }
}

class RepositoryUtente {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    

    suspend fun login(email: String, password: String): FirebaseUser?{
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        }catch (e: Exception){
            throw e
        }
    }

    suspend fun signUp(matricola: String, nome: String, cognome: String, dataNascita: String, email: String, password: String): FirebaseUser?{
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val utente = result.user ?: return null

            val profiloUtente = ProfiloUtente(utente.uid, matricola, nome, cognome, dataNascita, email)
            firestore.collection("utenti").document(utente.uid).set(profiloUtente).await()

            return utente

        }catch (e: FirebaseAuthUserCollisionException){
            //l'email è già in uso
            throw EmailAlreadyInUseException()
        }catch (e: Exception){
            // per tutti gli altri errori che possono verificarsi
            throw e
        }
    }
    suspend fun resetPassword(email: String){
        auth.useAppLanguage()
        try {
            auth.sendPasswordResetEmail(email).await()
        }catch (e: Exception){
            throw e
        }
    }
    suspend fun isFirstLogin(userId: String): Boolean{
        val documento = firestore.collection("utenti").document(userId).get().await()
        val profiloUtente = documento.toObject(ProfiloUtente::class.java)
        return profiloUtente?.primoAccesso ?: true
    }
    suspend fun updateFirstLogin(userId: String){
        val riferimentoDocumento = firestore.collection("utenti").document(userId)
        riferimentoDocumento.update("primoAccesso", false).await()
    }


    suspend fun getUtenteAttuale(): FirebaseUser?{
        val utenteCorrente = auth.currentUser
        return utenteCorrente
    }

    suspend fun sendEmailVerification(){
        auth.useAppLanguage()
        try {
            val utente = getUtenteAttuale()
            utente?.sendEmailVerification()
        }catch (e: Exception){
            throw e
        }
    }
    //Andrebbe tolta
    suspend fun setPreferenzeLinguaEmail(){
        auth.useAppLanguage()
    }

    suspend fun getUserProfile(userId: String): ProfiloUtente? {
            val document = firestore.collection("utenti").document(userId).get().await()
            return document.toObject(ProfiloUtente::class.java)
    }

    suspend fun updateUserProfile(profiloUtente: ProfiloUtente) {
            firestore.collection("utenti").document(profiloUtente.id).set(profiloUtente).await()
    }
    suspend fun isEmailVerified(): Boolean {
        val utenteAttuale = auth.currentUser
        utenteAttuale?.reload()?.await()
        return utenteAttuale?.isEmailVerified ?: false
    }
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

}

class EmailAlreadyInUseException : Exception("L'indirizzo email è già in uso")





