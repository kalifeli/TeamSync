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


class RepositoryUtente{
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    /**
     * Effettua il login di un utente con l'email e la password fornite.
     *
     * @param email L'indirizzo email dell'utente.
     * @param password La password dell'utente.
     * @return Un [FirebaseUser] se il login ha successo, null altrimenti.
     * @throws Exception se il login fallisce.
     * */
    suspend fun login(email: String, password: String): FirebaseUser?{
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        }catch (e: Exception){
            throw e
        }
    }

    /**
     * Registra un nuovo utente con i dettagli forniti.
     *
     * @param matricola La matricola dell'utente.
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     * @param dataNascita La data di nascita dell'utente.
     * @param sesso Il sesso dell'utente.
     * @param email L'indirizzo email dell'utente.
     * @param password La password dell'utente.
     * @return Un [FirebaseUser] se la registrazione ha successo, null altrimenti.
     * @throws EmailAlreadyInUseException se l'email è già in uso.
     * @throws Exception per tutti gli altri errori che possono verificarsi.
     */
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

    /**
     * Invia una email di reset della password all'utente con l'indirizzo email fornito.
     *
     * @param email L'indirizzo email dell'utente.
     * @throws Exception se il reset della password fallisce.
     */
    suspend fun resetPassword(email: String){
        auth.useAppLanguage()
        try {
            auth.sendPasswordResetEmail(email).await()
        }catch (e: Exception){
            throw e
        }
    }

    /**
     * Verifica se l'utente sta effettuando il primo accesso.
     *
     * @param userId L'ID dell'utente.
     * @return True se è il primo accesso, false altrimenti.
     * @throws Exception se il controllo fallisce.
     */
    suspend fun isFirstLogin(userId: String): Boolean{
        val documento = firestore.collection("utenti").document(userId).get().await()
        val profiloUtente = documento.toObject(ProfiloUtente::class.java)
        return profiloUtente?.primoAccesso ?: true
    }

    /**
     * Aggiorna lo stato del primo accesso per l'utente con l'ID fornito.
     *
     * @param userId L'ID dell'utente.
     * @throws Exception se l'aggiornamento fallisce.
     */

    suspend fun updateFirstLogin(userId: String){
        val riferimentoDocumento = firestore.collection("utenti").document(userId)
        riferimentoDocumento.update("primoAccesso", false).await()
    }

    /**
     * Ottiene l'utente attualmente loggato.
     *
     * @return Un [FirebaseUser] se un utente è loggato, null altrimenti.
     */
    fun getUtenteAttuale(): FirebaseUser?{
        val utenteCorrente = auth.currentUser
        return utenteCorrente
    }

    /**
     * Invia un'email di verifica all'utente attualmente loggato.
     *
     * @throws Exception se l'invio dell'email fallisce.
     */
    fun sendEmailVerification(){
        auth.useAppLanguage()
        try {
            val utente = getUtenteAttuale()
            utente?.sendEmailVerification()
        }catch (e: Exception){
            throw e
        }
    }

    /**
     * Ottiene il profilo dell'utente con l'ID fornito.
     *
     * @param userId L'ID dell'utente.
     * @return Un [ProfiloUtente] se il profilo è trovato, null altrimenti.
     * @throws Exception se il recupero del profilo fallisce.
     */
     suspend fun getUserProfile(userId: String): ProfiloUtente? {
            val document = firestore.collection("utenti").document(userId).get().await()
            return document.toObject(ProfiloUtente::class.java)
    }

    /**
     * Ottiene il profilo utente in modo sincrono.
     *
     * @param userId L'ID dell'utente.
     * @param callback Funzione di callback che riceve il [ProfiloUtente] o null in caso di errore.
     */
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


    /**
     * Aggiorna il profilo dell'utente con i nuovi dettagli forniti.
     *
     * @param profiloUtente Il profilo utente con i nuovi dettagli.
     * @throws Exception se l'aggiornamento fallisce.
     */
    suspend fun updateUserProfile(profiloUtente: ProfiloUtente) {
            firestore.collection("utenti").document(profiloUtente.id).set(profiloUtente).await()
    }

    /**
     * Ottiene tutti gli ID degli utenti.
     *
     * @return Una lista di ID degli utenti.
     * @throws Exception se il recupero degli ID fallisce.
     */
    suspend fun getallutenti(): List<String> {
        val utentiCollection = Firebase.firestore.collection("utenti")

        return try {
            val querySnapshot = utentiCollection.get().await()
            val utentiList = mutableListOf<String>()

            for (document in querySnapshot.documents) {
                val nomeUtente = document.getString("id")
                if (nomeUtente != null) {
                    utentiList.add(nomeUtente)
                }
            }

            utentiList
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Verifica se l'email dell'utente attualmente loggato è verificata.
     *
     * @return True se l'email è verificata, false altrimenti.
     * @throws Exception se la verifica fallisce.
     */
    suspend fun isEmailVerified(): Boolean {
        val utenteAttuale = auth.currentUser
        utenteAttuale?.reload()?.await()
        return utenteAttuale?.isEmailVerified ?: false
    }

    /**
     * Effettua il logout dell'utente attualmente loggato.
     */
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    suspend fun eliminaAccount(userId: String): String? {
        return try {
            // step 1: Eliminazione dei dati dell'utente da Firestore
            firestore.collection("utenti").document(userId)
                .delete().await()

            // step 2: Eliminazione dell'utente da Firebase Authentification
            val utente = auth.currentUser
            if(utente != null && utente.uid == userId){
                utente.delete().await()
                null
            }else{
                "Utente non autentificato o user ID incorretto"
            }

        }catch (e: Exception){
            "Errore: ${e.message}"
        }

    }

    /**
     * Aggiunge un amico all'utente con l'ID fornito.
     *
     * @param userId L'ID dell'utente.
     * @param amicoId L'ID dell'amico da aggiungere.
     * @throws Exception se l'aggiunta dell'amico fallisce.
     */
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

    /**
     * Rimuove un amico dall'utente con l'ID fornito.
     *
     * @param userId L'ID dell'utente.
     * @param amicoId L'ID dell'amico da rimuovere.
     * @throws Exception se la rimozione dell'amico fallisce.
     */

    suspend fun rimuoviAmico(userId: String, amicoId: String) {
        try {
            val userRef = firestore.collection("utenti").document(userId)
            val snapshot = userRef.get().await()
            val userProfile = snapshot.toObject(ProfiloUtente::class.java)

            // Verifica se il profilo esiste e rimuove amicoId dalla lista degli amici se presente
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

/**
 * Eccezione per l'email già in uso.
 */
class EmailAlreadyInUseException : Exception("L'indirizzo email è già in uso")





