package com.example.teamsync.caratteristiche.login.data.repository

import android.net.Uri
import com.example.teamsync.caratteristiche.login.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.login.data.model.SessoUtente
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.util.Date
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

    suspend fun signUp(matricola: String, nome: String, cognome: String, dataNascita: Date, sesso: SessoUtente, email: String, password: String): FirebaseUser?{
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val utente = result.user ?: return null

            val profiloUtente = ProfiloUtente(utente.uid, nome, cognome, matricola, dataNascita, sesso, email)
            firestore.collection("utenti").document(utente.uid).set(profiloUtente).await()

            utente

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


    fun getUtenteAttuale(): FirebaseUser?{
        val utenteCorrente = auth.currentUser
        return utenteCorrente
    }


    fun sendEmailVerification(){
        auth.useAppLanguage()
        try {
            val utente = getUtenteAttuale()
            utente?.sendEmailVerification()
        }catch (e: Exception){
            throw e
        }
    }

     suspend fun getUserProfile(userId: String): ProfiloUtente? {
            val document = firestore.collection("utenti").document(userId).get().await()
            return document.toObject(ProfiloUtente::class.java)
    }

    fun getUserSincrono(userId: String, callback: (ProfiloUtente?) -> Unit) {
         firestore.collection("utenti").document(userId).get()
            .addOnSuccessListener { documentSnapshot ->
                val profile = documentSnapshot.toObject(ProfiloUtente::class.java)
                callback(profile)
            }
            .addOnFailureListener {
                // Gestione dell'errore, ad esempio log o callback con null
                callback(null)
            }
    }


    suspend fun updateUserProfile(profiloUtente: ProfiloUtente) {
            firestore.collection("utenti").document(profiloUtente.id).set(profiloUtente).await()
    }

    suspend fun getallutenti(): List<String> {
        val utentiCollection = Firebase.firestore.collection("utenti")

        return try {
            val querySnapshot = utentiCollection.get().await()
            val utentiList = mutableListOf<String>()

            for (document in querySnapshot.documents) {
                // Assuming 'name' is a field in your Firestore documents
                val nomeUtente = document.getString("id")
                if (nomeUtente != null) {
                    utentiList.add(nomeUtente)
                }
            }

            utentiList
        } catch (e: Exception) {
            // Gestisci eventuali eccezioni qui
            emptyList() // o altra gestione dell'errore desiderata
        }
    }



    suspend fun isEmailVerified(): Boolean {
        val utenteAttuale = auth.currentUser
        utenteAttuale?.reload()?.await()
        return utenteAttuale?.isEmailVerified ?: false
    }
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    suspend fun aggiungiAmico(userId: String, amicoId: String) {
        try {
            val userRef = firestore.collection("utenti").document(userId)
            val snapshot = userRef.get().await()
            val userProfile = snapshot.toObject(ProfiloUtente::class.java)

            // Verifica se il profilo esiste e aggiungi amicoId alla lista degli amici se non è già presente
            userProfile?.let {
                val updatedAmici = it.amici.toMutableList()
                if (!updatedAmici.contains(amicoId)) {
                    updatedAmici.add(amicoId)
                    userRef.update("amici", updatedAmici).await()
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
    suspend fun rimuoviAmico(userId: String, amicoId: String) {
        try {
            val userRef = firestore.collection("utenti").document(userId)
            val snapshot = userRef.get().await()
            val userProfile = snapshot.toObject(ProfiloUtente::class.java)

            // Verifica se il profilo esiste e rimuovi amicoId dalla lista degli amici se presente
            userProfile?.let {
                val updatedAmici = it.amici.toMutableList()
                if (updatedAmici.contains(amicoId)) {
                    updatedAmici.remove(amicoId)
                    userRef.update("amici", updatedAmici).await()
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}

class EmailAlreadyInUseException : Exception("L'indirizzo email è già in uso")





