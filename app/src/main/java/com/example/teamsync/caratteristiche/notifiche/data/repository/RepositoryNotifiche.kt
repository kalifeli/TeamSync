package com.example.teamsync.caratteristiche.notifiche.data.repository

import android.util.Log
import com.example.teamsync.caratteristiche.notifiche.data.model.Notifiche
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.util.UUID


/**
 * Repository per gestire le operazioni di CRUD sulle notifiche.
 */
class RepositoryNotifiche {

    private val firestore: FirebaseFirestore = Firebase.firestore

    /**
     * Recupera una lista di notifiche per un utente specifico da Firestore.
     *
     * @param userId L'ID dell'utente per il quale si vogliono recuperare le notifiche.
     *               Se `null`, viene restituita una lista vuota.
     * @return Una lista di oggetti [Notifiche] per l'utente specificato.
     *         Restituisce una lista vuota se l'utente non ha notifiche o se si verifica un'eccezione.
     * @throws Exception Se si verifica un errore durante il recupero delle notifiche da Firestore.
     */
    suspend fun getNotificheUtente(userId: String?): List<Notifiche> {
        if (userId == null) return emptyList()
        return try {
            val snapshot = firestore.collection("Notifiche")
                .whereEqualTo("destinatario", userId)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(Notifiche::class.java) }
        } catch (e: Exception) {
            Log.e("RepositoryNotifiche", "Errore durante il recupero delle notifiche per l'utente $userId", e)
            emptyList()
        }
    }

    /**
     * Crea una nuova notifica nella collezione "Notifiche" di Firestore.
     *
     * @param mittenteId L'ID dell'utente che invia la notifica.
     * @param destinatarioId L'ID dell'utente che riceve la notifica.
     * @param tipo Il tipo di notifica.
     * @param contenuto Il contenuto della notifica.
     * @param progetto L'ID del progetto a cui si riferisce la notifica.
     * @throws Exception Se si verifica un errore durante la creazione della notifica.
     */
    fun creaNotifica(mittenteId: String, destinatarioId: String, tipo: String, contenuto: String, progetto: String) {
        try {
            val notificaId = UUID.randomUUID().toString()
            val notifica: Notifiche = if(tipo == "Richiesta_Amicizia" || tipo == "Richiesta_Progetto") {
                Notifiche(mittenteId, destinatarioId, tipo, false, contenuto,notificaId,progetto,"false")
            } else {
                Notifiche(mittenteId, destinatarioId, tipo, false, contenuto,notificaId,progetto)

            }
            firestore.collection("Notifiche").document(notificaId)
                .set(notifica)
                .addOnSuccessListener {
                    Log.d("RepositoryNotifiche", "Notifica inserita con successo con ID: $notificaId")
                }
                .addOnFailureListener { e ->
                    Log.e("RepositoryNotifiche", "Errore durante l'inserimento della notifica con ID: $notificaId", e)
                    throw e
                }
        } catch (e: Exception) {
            Log.e("RepositoryNotifiche", "Eccezione durante la creazione della notifica", e)
            throw e
        }
    }

    /**
     * Elimina una notifica specifica dalla collezione "Notifiche" di Firestore.
     *
     * @param notificaId L'ID della notifica da eliminare.
     * @throws Exception Se si verifica un errore durante l'eliminazione della notifica.
     */
    suspend fun deleteNotifica(notificaId: String) {
        try {
            val docRef = firestore.collection("Notifiche").document(notificaId)
            docRef.delete().await()
            Log.d("RepositoryNotifiche", "Notifica con ID $notificaId eliminata con successo")
        } catch (e: Exception) {
            Log.e("RepositoryNotifiche", "Errore durante l'eliminazione della notifica con ID $notificaId", e)
            throw e
        }
    }

    /**
     * Elimina tutte le notifiche di un utente specifico dalla collezione "Notifiche" di Firestore.
     *
     * @param userId L'ID dell'utente di cui si vogliono eliminare tutte le notifiche.
     * @throws Exception Se si verifica un errore durante l'eliminazione delle notifiche.
     */
    suspend fun deleteAllNotifiche(userId: String){
        try {
            val snapshot = firestore.collection("Notifiche")
                .whereEqualTo("destinatario",userId)
                .get()
                .await()

            val batch = firestore.batch() // permette di effettuare più operazioni di scrittura come un'unica operazione atomica

            snapshot.documents.forEach{ document ->
                batch.delete(document.reference)
            }
            batch.commit().await()
        }catch (e: Exception){
            Log.e("RepositoryNotifiche", "Errore durante l'eliminazione delle notifiche per l'utente con ID $userId", e)
            throw e
        }
    }

    /**
     * Segna una notifica come aperta.
     *
     * @param notificaId L'ID della notifica da segnare come aperta.
     * @throws Exception Se si verifica un errore durante l'aggiornamento della notifica.
     */
    suspend fun apriNotifica(notificaId: String) {
        try {
            val docRef = firestore.collection("Notifiche").document(notificaId)
            val notifica = docRef.get().await().toObject<Notifiche>()
            if (notifica != null) {
                notifica.aperto = true
                docRef.set(notifica).await()
                println("Stato della notifica con ID $notificaId cambiato in aperto")
            } else {
                println("Notifica con ID $notificaId non trovata")
            }
        } catch (e: Exception) {
            Log.e("RepositoryNotifiche", "Errore durante l'aggiornamento della notifica con ID $notificaId", e)
            throw e
        }
    }

    /**
     * Recupera l'ID di una notifica in base al contenuto specificato.
     *
     * @param contenuto Il contenuto della notifica da cercare.
     * @return L'ID della notifica se trovata, altrimenti null.
     * @throws Exception Se si verifica un errore durante il recupero della notifica.
     */
    suspend fun getNotificaIdByContent(contenuto: String): String? {
        try {
            val querySnapshot = firestore.collection("Notifiche")
                .whereEqualTo("contenuto", contenuto)
                .limit(1)
                .get()
                .await()

            if (querySnapshot.documents.isNotEmpty()) {
                return querySnapshot.documents[0].id
            }
        } catch (e: Exception) {
            Log.e("RepositoryNotifiche", "Errore durante il recupero della notifica con contenuto: $contenuto", e)
            throw e
        }
        return null
    }

    /**
     * Aggiorna una notifica esistente nella collezione "Notifiche" di Firestore.
     *
     * @param notifica L'oggetto [Notifiche] contenente i dati aggiornati.
     * @throws Exception Se si verifica un errore durante l'aggiornamento della notifica.
     */
    suspend fun updateNotifica(notifica: Notifiche) {
        try {
            val notificaRef = firestore.collection("Notifiche").document(notifica.id)
            notificaRef.set(notifica).await()
            Log.d("RepositoryNotifiche", "Notifica aggiornata con successo su Firebase: ${notifica.id}")
        } catch (e: Exception) {
            Log.e("RepositoryNotifiche", "Errore durante l'aggiornamento della notifica con ID ${notifica.id}", e)
            throw e
        }
    }

    /**
     * Recupera una notifica di richiesta di amicizia specifica tra un mittente e un destinatario.
     *
     * @param mittenteId L'ID dell'utente che ha inviato la richiesta di amicizia.
     * @param destinatarioId L'ID dell'utente che ha ricevuto la richiesta di amicizia.
     * @return Un oggetto [Notifiche] se la richiesta di amicizia esiste, altrimenti null.
     * @throws Exception Se si verifica un errore durante il recupero della notifica da Firestore.
     */
    suspend fun getNotificaRichiestaAmicizia(mittenteId: String, destinatarioId: String): Notifiche? {
        return try {
            // Recupera la notifica di richiesta di amicizia
            val querySnapshot =  firestore.collection("Notifiche")
                .whereEqualTo("mittente", mittenteId)
                .whereEqualTo("destinatario", destinatarioId)
                .whereEqualTo("tipo", "Richiesta_Amicizia")
                .get()
                .await()
            val documents = querySnapshot.documents
            if (documents.isNotEmpty()) {
                // Converte il primo documento in un oggetto Notifiche
                documents[0].toObject(Notifiche::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            // Gestionr  dell'eccezione. In caso di errore ritorna null.
            Log.e("RepositoryNotifiche", "Errore durante il recupero della notifica di richiesta di amicizia", e)
            null
        }
    }

    /**
     * Elimina tutte le notifiche di amicizia tra un mittente e un destinatario specifici.
     * Le notifiche di amicizia possono includere richieste di amicizia e accettazioni di amicizia.
     *
     * @param mittenteId L'ID dell'utente che ha inviato la richiesta o l'accettazione di amicizia.
     * @param destinatarioId L'ID dell'utente che ha ricevuto la richiesta o l'accettazione di amicizia.
     * @throws Exception Se si verifica un errore durante l'eliminazione delle notifiche da Firestore.
     */
    suspend fun deleteAmiciziaNotifiche(mittenteId: String, destinatarioId: String) {
        try {
            val querySnapshot = firestore.collection("Notifiche")
                .whereEqualTo("mittente", mittenteId)
                .whereEqualTo("destinatario", destinatarioId)
                .whereIn("tipo", listOf("Richiesta_Amicizia", "Accetta_Amicizia"))
                .get()
                .await()
            val batch = firestore.batch()
            for (document in querySnapshot.documents) {
                batch.delete(document.reference)
            }
            batch.commit().await()
            Log.d("RepositoryNotifiche","Notifiche di amicizia tra $mittenteId e $destinatarioId eliminate con successo")
        } catch (e: Exception) {
            Log.e("RepositoryNotifiche", "Errore durante l'eliminazione delle notifiche di amicizia", e)
            throw e
        }
    }
}


