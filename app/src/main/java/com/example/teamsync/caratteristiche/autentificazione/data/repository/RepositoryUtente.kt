package com.example.teamsync.caratteristiche.autentificazione.data.repository

import android.content.Context
import com.example.teamsync.R
import com.example.teamsync.caratteristiche.autentificazione.data.model.ProfiloUtente
import com.example.teamsync.caratteristiche.autentificazione.data.model.SessoUtente
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.util.Date


/**
 * Repository per gestire le operazioni relative alle attività.
 */
class RepositoryUtente(val contesto: Context){

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
            throw EmailAlreadyInUseException(contesto)
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
        return auth.currentUser
    }

    fun getUtenteAttualeID(): String? {
        val utenteCorrente: FirebaseUser? = auth.currentUser
        return utenteCorrente?.uid
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
                // Gestione dell'errore
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
    suspend fun getAllUtenti(): List<String> {
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

    /**
     * Elimina l'account di un utente dato il suo ID.
     *
     * Questa funzione esegue due passaggi per eliminare completamente un account utente:
     * 1. Elimina i dati dell'utente dal database Firestore.
     * 2. Se l'utente attualmente autenticato ha lo stesso ID fornito, elimina anche l'account
     *    dall'autenticazione di Firebase.
     *
     * Se l'eliminazione dell'account ha successo, la funzione restituisce `null`. In caso di errori
     * durante l'eliminazione dei dati o dell'account, restituisce un messaggio di errore.
     *
     * @param userId L'ID dell'utente di cui si vuole eliminare l'account.
     * @return Una stringa contenente un messaggio di errore in caso di fallimento, oppure `null` se l'eliminazione ha successo.
     */

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
                contesto.getString(R.string.errore_id_utente)
            }

        }catch (e: Exception){
            contesto.getString(R.string.errore_generico)
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

    /**
     * Verifica l'esistenza dei profili associati alla lista di amici e rimuove quelli che non esistono più nel database.
     *
     * Questa funzione scorre l'elenco degli ID amici forniti, controlla l'esistenza di ciascun profilo utente
     * nel database e rimuove gli amici che non esistono più. La funzione aggiorna la lista degli amici validi
     * nel database dell'utente e restituisce la lista aggiornata.
     *
     * @param userId L'ID dell'utente proprietario della lista di amici.
     * @param amici Una lista di ID amici da verificare.
     * @return Una lista aggiornata di ID amici che esistono ancora nel database.
     * @throws Exception se si verifica un errore durante il recupero o la rimozione dei profili.
     */
    suspend fun rimuoviAmiciNonEsistenti(userId: String, amici: List<String>): List<String> {
        val amiciValidi = amici.toMutableList()
        for (amicoId in amici) {
            val amico = getUserProfile(amicoId)
            if (amico == null) {
                // Rimuovi l'amico non esistente
                rimuoviAmico(userId, amicoId)
                amiciValidi.remove(amicoId)
            }
        }
        return amiciValidi
    }
}

/**
 * Eccezione per l'email già in uso.
 */
class EmailAlreadyInUseException(contesto: Context) : Exception(contesto.getString(R.string.errore_email_in_uso))





