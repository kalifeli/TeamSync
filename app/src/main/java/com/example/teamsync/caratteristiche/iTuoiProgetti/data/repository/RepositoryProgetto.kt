package com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository

import com.example.teamsync.caratteristiche.iTuoiProgetti.data.model.Progetto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class RepositoryProgetto {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Funzione che recupera la lista dei progetti a cui partecipa un utente specifico.
     *
     * @param userId L'ID dell'utente di cui si vogliono recuperare i progetti.
     * @return Una lista di oggetti `Progetto` a cui l'utente partecipa. Restituisce una lista vuota in caso di errore.
     *
     * La funzione utilizza Firestore per interrogare la collezione "progetti" e recuperare tutti i documenti
     * in cui il campo "partecipanti" contiene l'ID dell'utente specificato.
     * Se l'operazione ha successo, la lista di progetti viene restituita. In caso di eccezione, viene restituita una lista vuota.
     * La funzione è una funzione sospesa e deve essere chiamata all'interno di una coroutine o di un'altra funzione sospesa.
     */
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

    /**
     * Funzione che crea un nuovo progetto nel database Firestore.
     *
     * @param progetto Un'istanza della classe `Progetto` che contiene i dettagli del progetto da creare.
     * @return L'ID del documento creato in Firestore.
     *
     * La funzione utilizza Firestore per aggiungere un nuovo documento alla collezione "progetti".
     * Se l'operazione ha successo, l'ID del documento creato viene restituito.
     * La funzione è una funzione sospesa e deve essere chiamata all'interno di una coroutine o di un'altra funzione sospesa.
     *
     * @throws Exception Se si verifica un errore durante l'aggiunta del documento su Firestore, l'eccezione sarà rilanciata.
     */
    suspend fun creaProgetto(progetto: Progetto): String {
        return try {
            val documentRef = firestore.collection("progetti").add(progetto).await()
            documentRef.id
        } catch (e: Exception) {
            throw e
        }
    }
    /**
     * Funzione che permette di aggiungere un partecipante a un progetto.
     *
     * @param progettoId L'ID del progetto a cui si vuole aggiungere il partecipante. Può essere null, ma in tal caso la funzione non farà nulla.
     * @param userId L'ID dell'utente che si vuole aggiungere come partecipante al progetto. Può essere null, ma in tal caso la funzione non farà nulla.
     *
     * La funzione utilizza Firestore per aggiungere l'utente alla lista dei partecipanti del progetto specificato.
     * Se uno dei parametri è null, il documento del progetto non sarà aggiornato.
     * La funzione è una funzione sospesa e deve essere chiamata all'interno di una coroutine o di un'altra funzione sospesa.
     *
     * @throws Exception Se si verifica un errore durante l'aggiornamento del documento su Firestore, l'eccezione sarà rilanciata.
     */
    suspend fun aggiungiPartecipante(progettoId: String?, userId: String?) {
        try {
            val progettoRef = firestore.collection("progetti").document(progettoId ?: "")
            progettoRef.update("partecipanti", FieldValue.arrayUnion(userId)).await()
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Funzione che permette a un utente di abbandonare un progetto.
     *
     * @param userId L'ID dell'utente che vuole abbandonare il progetto. Può essere null, ma in tal caso la funzione non farà nulla.
     * @param progettoId L'ID del progetto che l'utente vuole abbandonare. Può essere null, ma in tal caso la funzione non farà nulla.
     *
     * La funzione utilizza Firestore per rimuovere l'utente dalla lista dei partecipanti del progetto specificato.
     * Se uno dei parametri è null, il documento del progetto non sarà aggiornato.
     * La funzione è una funzione sospesa e deve essere chiamata all'interno di una coroutine o di un'altra funzione sospesa.
     *
     * @throws Exception Se si verifica un errore durante l'aggiornamento del documento su Firestore, l'eccezione sarà rilanciata.
     */
    suspend fun abbandonaProgetto(userId: String?, progettoId: String){
        try {
            val progettoRef = firestore.collection("progetti").document(progettoId)
            progettoRef.update("partecipanti", FieldValue.arrayRemove(userId)).await()
            if(getPartecipantiDelProgetto(progettoId).isEmpty()){
                eliminaProgetto(progettoId)
            }
        }catch (e:Exception){
            throw e
        }
    }

    /**
     * Funzione che recupera l'utente attualmente autenticato.
     *
     * @return Un oggetto `FirebaseUser` che rappresenta l'utente attualmente autenticato, oppure `null` se non c'è alcun utente autenticato.
     *
     * La funzione utilizza Firebase Authentication per ottenere l'utente attualmente autenticato tramite la proprietà `currentUser`.
     * Se l'operazione ha successo, l'utente attualmente autenticato viene restituito. In caso di eccezione, l'eccezione viene rilanciata.
     *
     * @throws Exception Se si verifica un errore durante il recupero dell'utente attualmente autenticato, l'eccezione sarà rilanciata.
     */
    fun getUtenteCorrente(): FirebaseUser? {
        return try {
            auth.currentUser
        }catch (e: Exception){
            throw  e
        }
    }

    /**
     * Funzione che genera un codice univoco per un progetto.
     *
     * @return Una stringa di 8 caratteri che rappresenta un codice univoco per il progetto.
     *
     * La funzione utilizza la classe `UUID` per generare un UUID casuale e restituisce i primi 8 caratteri della rappresentazione in stringa dell'UUID.
     * Questo codice può essere utilizzato per identificare in modo univoco un progetto.
     */
    fun generaCodiceProgetto(): String{
        return UUID.randomUUID().toString().substring(0, 8)
    }

    /**
     * Funzione che recupera l'ID di un progetto in base a un codice specificato.
     *
     * @param codice Il codice del progetto di cui si vuole ottenere l'ID.
     * @return L'ID del progetto corrispondente al codice specificato, oppure `null` se non viene trovato alcun progetto o in caso di errore.
     *
     * La funzione utilizza Firestore per interrogare la collezione "progetti" e cercare un documento in cui il campo "codice" corrisponde al valore specificato.
     * Se l'operazione ha successo e viene trovato un progetto, l'ID del progetto viene restituito. In caso di eccezione o se non viene trovato alcun progetto, viene restituito `null`.
     * La funzione è una funzione sospesa e deve essere chiamata all'interno di una coroutine o di un'altra funzione sospesa.
     */
    suspend fun getProgettoIdByCodice(codice: String): String?{
         return try {
            val progetto = firestore.collection("progetti")
                .whereEqualTo("codice", codice)
                .get()
                .await()
                .toObjects(Progetto::class.java)
             if(progetto.isNotEmpty()) {
                 progetto[0].id
             }else{
                 null
             }
        }catch (e: Exception){
            null
        }
    }

    /**
     * Funzione che esegue il logout dell'utente attualmente autenticato.
     *
     * La funzione utilizza Firebase Authentication per eseguire il logout dell'utente attualmente autenticato chiamando il metodo `signOut` sull'istanza `auth`.
     * Dopo la chiamata a questa funzione, l'utente non sarà più autenticato nell'app.
     */
    fun logout(){
        auth.signOut()
    }

    suspend fun getPartecipantiDelProgetto(progettoId: String): List<String> {
        return try {

            val docSnapshot = firestore.collection("progetti").document(progettoId).get().await()

            // Verifica se il documento esiste
            if (docSnapshot.exists()) {
                // Ottieni il campo "partecipanti" dal documento
                val partecipanti = docSnapshot.get("partecipanti") as? List<String>
                partecipanti ?: emptyList() // Restituisci la lista dei partecipanti, se presente, altrimenti una lista vuota
            } else {
                emptyList() // Se il documento non esiste, restituisci una lista vuota
            }
        } catch (e: Exception) {
            throw e
        }
    }
     private fun eliminaProgetto(progettoId: String){
        try {
            firestore.collection("progetti")
                .document(progettoId)
                .delete()
        }catch (e: Exception){
            throw e
        }
    }

    suspend fun getProgettoById(progettoId: String): Progetto? {
        return try {
            val documentSnapshot = firestore.collection("progetti").document(progettoId).get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(Progetto::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun aggiornaProgetto(progetto: Progetto){
        try {
            progetto.id?.let { id ->
                firestore.collection("progetti").document(id).set(progetto).await()
            }
        }catch (e: Exception){
            throw Exception("Errore durante l'aggiornamento del progetto: ${e.message}")

        }
    }


}

