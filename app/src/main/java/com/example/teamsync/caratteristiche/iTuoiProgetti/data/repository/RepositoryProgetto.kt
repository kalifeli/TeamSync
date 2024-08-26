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
     * Recupera la lista dei progetti completati a cui partecipa un utente specifico.
     *
     * @param userId L'ID dell'utente di cui si vogliono recuperare i progetti completati.
     * @return Una lista di oggetti `Progetto` completati a cui l'utente partecipa. Restituisce una lista vuota in caso di errore.
     */
    suspend fun getProgettiCompletatiUtente(userId: String): List<Progetto>{
        return try {
            firestore.collection("progetti")
                .whereArrayContains("partecipanti", userId)
                .whereEqualTo("completato", true)
                .get()
                .await()
                .toObjects(Progetto::class.java)
        }catch (e: Exception){
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
     * @pxaram progettoId L'ID del progetto che l'utente vuole abbandonare. Può essere null, ma in tal caso la funzione non farà nulla.
     *
     * La funzione utilizza Firestore per rimuovere l'utente dalla lista dei partecipanti del progetto specificato.
     * Se uno dei parametri è null, il documento del progetto non sarà aggiornato.
     * La funzione è una funzione sospesa e deve essere chiamata all'interno di una coroutine o di un'altra funzione sospesa.
     *
     * @throws Exception Se si verifica un errore durante l'aggiornamento del documento su Firestore, l'eccezione sarà rilanciata.
     */

    suspend fun abbandonaProgetto(userId: String?, progettoId: String, callback: (Boolean) -> Unit) {
        try {
            val progettoRef = firestore.collection("progetti").document(progettoId)
            progettoRef.update("partecipanti", FieldValue.arrayRemove(userId)).await()

            val listaPartecipanti = getPartecipantiDelProgetto(progettoId)

            if (listaPartecipanti.isEmpty())
            {
                // Se non ci sono più partecipanti, elimina il progetto
                eliminaProgetto(progettoId)
                callback(true)
            }else{
                //Se ci sono ancora partecipanti, viene chiamata comunque la callback e impostata a false
                callback(false)
            }
        } catch (e: Exception) {
            callback(false)
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
        return auth.currentUser
    }

    /**
     * Funzione che genera un codice univoco per un progetto.
     *
     * @return Una stringa di 8 caratteri che rappresenta un codice univoco per il progetto.
     *
     * La funzione utilizza la classe `UUID` per generare un UUID casuale e restituisce i primi 8 caratteri della rappresentazione in stringa dell'UUID.
     * Questo codice può essere utilizzato per identificare in modo univoco un progetto.
     */
    fun generaCodiceProgetto(): String {
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
    suspend fun getProgettoIdByCodice(codice: String): String? {
        return try {
            val progetto = firestore.collection("progetti")
                .whereEqualTo("codice", codice)
                .get()
                .await()
                .toObjects(Progetto::class.java)
            if (progetto.isNotEmpty()) {
                progetto[0].id
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Recupera la lista dei partecipanti di un progetto.
     *
     * @param progettoId L'ID del progetto di cui si vogliono recuperare i partecipanti.
     * @return Una lista di ID degli utenti partecipanti al progetto.
     * @throws Exception Se si verifica un errore durante l'operazione.
     */
    suspend fun getPartecipantiDelProgetto(progettoId: String): List<String> {
        return try {
            val docSnapshot = firestore.collection("progetti").document(progettoId).get().await()

            // Verifica se il documento esiste
            if (docSnapshot.exists()) {
                // Ottieni il campo "partecipanti" dal documento
                val partecipanti = docSnapshot.get("partecipanti")

                // Effettua il cast in modo sicuro e restituisce la lista
                (partecipanti as? List<*>)?.filterIsInstance<String>() ?: emptyList()
            } else {
                emptyList() // Se il documento non esiste, restituisce una lista vuota
            }
        } catch (e: Exception) {
            throw e
        }
    }


    /**
     * Elimina un progetto dal database.
     *
     * @param progettoId L'ID del progetto da eliminare.
     * @throws Exception Se si verifica un errore durante l'operazione.
     */
    private fun eliminaProgetto(progettoId: String) {
        try {

            firestore.collection("progetti")
                .document(progettoId)
                .delete()

        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Elimina le notifiche associate a un progetto.
     *
     * @param progettoId L'ID del progetto di cui si vogliono eliminare le notifiche.
     * @throws Exception Se si verifica un errore durante l'operazione.
     */
    suspend fun eliminaNotificheDelProgetto(progettoId: String) {
        try {
            // Ottieni tutte le notifiche associate al progetto
            val querySnapshot = firestore.collection("Notifiche")
                .whereEqualTo("progetto_id", progettoId)
                .get()
                .await()

            // Itera su tutte le notifiche e elimina ciascuna
            for (document in querySnapshot.documents) {
                val notificaId = document.id
                firestore.collection("Notifiche").document(notificaId).delete().await()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Recupera un progetto dato il suo ID.
     *
     * @param progettoId L'ID del progetto da recuperare.
     * @return L'oggetto `Progetto` corrispondente all'ID specificato, oppure `null` se non viene trovato alcun progetto.
     * @throws Exception Se si verifica un errore durante l'operazione.
     */
    suspend fun getProgettoById(progettoId: String): Progetto? {
        return try {
            val documentSnapshot =
                firestore.collection("progetti").document(progettoId).get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(Progetto::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Aggiorna i dati di un progetto nel database.
     *
     * @param progetto Un'istanza della classe `Progetto` contenente i dati aggiornati.
     * @throws Exception Se si verifica un errore durante l'aggiornamento del documento su Firestore.
     */
    suspend fun aggiornaProgetto(progetto: Progetto) {
        try {
            progetto.id?.let { id ->
                firestore.collection("progetti").document(id).set(progetto).await()
            }
        } catch (e: Exception) {
            throw e

        }
    }

    /**
     * Recupera la lista dei progetti a cui partecipa un utente specifico utilizzando una callback.
     *
     * @param userId L'ID dell'utente di cui si vogliono recuperare i progetti.
     * @param callback La funzione di callback da eseguire con la lista dei progetti.
     */
    suspend fun progettiutenteCallback(userId: String, callback: (List<Progetto>) -> Unit) {
        try {
            val querySnapshot = firestore.collection("progetti")
                .whereArrayContains("partecipanti", userId)
                .get()
                .await()
            if (!querySnapshot.isEmpty) {
                val progettiUtente = querySnapshot.toObjects(Progetto::class.java)
                callback(progettiUtente)
            } else {
                callback(emptyList()) // Callback con lista vuota se non ci sono progetti
            }
        } catch (e: Exception) {
            callback(emptyList()) // Callback con lista vuota se si verifica un'eccezione
        }
    }
}
