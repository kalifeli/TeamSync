package com.example.teamsync.caratteristiche.leMieAttivita.data.repository

import android.net.Uri
import android.util.Log
import com.example.teamsync.caratteristiche.leMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.util.Priorita
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID

/**
 * Repository per gestire le operazioni relative alle attività.
 */
class ToDoRepository {
    private val database = FirebaseFirestore.getInstance()

    /**
     * Aggiunge una nuova attività al database.
     *
     * @param titolo Il titolo dell'attività.
     * @param descrizione La descrizione dell'attività.
     * @param dataScad La data di scadenza dell'attività.
     * @param priorita La priorità dell'attività.
     * @param utenti L'ID dell'utente assegnato all'attività.
     * @param progetto L'ID del progetto associato all'attività.
     */
    suspend fun addTodo(
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorita: Priorita,
        utenti : String,
        progetto: String
    ) {
        val leMieAttivita = LeMieAttivita(
            titolo = titolo,
            descrizione = descrizione,
            dataScadenza = dataScad,
            priorita = priorita,
            completato = false,
            progetto = progetto,
            utenti = listOf(utenti),
        )
        database.collection("Todo").add(leMieAttivita).await()
    }


    /**
     * Carica un file su Firebase Storage e restituisce l'URL di download.
     *
     * @param uri L'URI del file da caricare.
     * @return L'URL di download del file caricato.
     * @throws Exception Se si verifica un errore durante il caricamento del file.
     */
    suspend fun uploadFile(uri: Uri): String {
        return withContext(Dispatchers.IO) {
            val storageReference = FirebaseStorage.getInstance().reference.child("files/${UUID.randomUUID()}")
            val uploadTask = storageReference.putFile(uri)
            uploadTask.await()  // Attendi il completamento dell'upload
            storageReference.downloadUrl.await().toString()  // Ottieni l'URL di download
        }
    }

    /**
     * Recupera tutte le attività non completate dal database.
     *
     * @return Una lista di attività non completate.
     */
    suspend fun getNotCompletedTodo(): List<LeMieAttivita> {
        return try {
            val snapshot = database.collection("Todo")
                .whereEqualTo("completato", false)
                .orderBy("dataScadenza") // Ordina per data di scadenza
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(LeMieAttivita::class.java) }
        } catch (e: Exception) {
            Log.e("ToDoRepository", "Errore nel caricamento delle attività non completate", e)
            emptyList()
        }
    }

    /**
     * Recupera tutte le attività di un utente specifico per un dato progetto.
     *
     * @param idProg L'ID del progetto.
     * @param utenteId L'ID dell'utente.
     * @return Una lista di attività associate all'utente per il progetto specificato.
     */
    suspend fun getTodoByUtente(idProg: String, utenteId: String): List<LeMieAttivita> {
        val snapshot = database.collection("Todo")
            .whereEqualTo("progetto", idProg)
            .whereArrayContains("utenti", utenteId)
            .whereEqualTo("completato", false)
            .orderBy("dataScadenza") // Ordina per data di scadenza
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(LeMieAttivita::class.java) }
    }

    /**
     * Recupera tutte le attività completate dal database.
     *
     * @return Una lista di attività completate.
     */
    suspend fun getAllTodoCompletate(): List<LeMieAttivita> {
        val snapshot = database.collection("Todo")
            .whereEqualTo("completato", true)
            .orderBy("dataScadenza") // Ordina per data di scadenza
            .get()
            .await()
        return snapshot.documents.mapNotNull { it.toObject(LeMieAttivita::class.java) }
    }

    /**
     * Elimina un'attività dal database.
     *
     * @param id L'ID dell'attività da eliminare.
     * @throws Exception Se si verifica un errore durante l'eliminazione.
     */
    suspend fun deleteTodo(id: String) {
        try {
            database.collection("Todo").document(id).delete().await()
        } catch (e: Exception) {
            // Gestisci l'errore se necessario
            throw Exception("Errore durante l'eliminazione del Todo: ${e.message}")
        }
    }

    /**
     * Aggiorna un'attività nel database.
     *
     * @param id L'ID dell'attività da aggiornare.
     * @param titolo Il nuovo titolo dell'attività.
     * @param descrizione La nuova descrizione dell'attività.
     * @param dataScad La nuova data di scadenza dell'attività.
     * @param priorita La nuova priorità dell'attività.
     * @param progetto L'ID del progetto associato all'attività.
     * @param utenti La lista degli ID degli utenti assegnati all'attività.
     * @param fileUri L'URI del file associato all'attività.
     * @throws Exception Se si verifica un errore durante l'aggiornamento.
     */
    suspend fun updateTodo(
        id: String,
        titolo: String,
        descrizione: String,
        dataScad: Date,
        priorita: Priorita,
        progetto: String,
        utenti : List<String>,
        fileUri: String?

    ) {
        try {
            val updatedTodo = LeMieAttivita(
                id = id,
                titolo = titolo,
                descrizione = descrizione,
                dataScadenza = dataScad,
                priorita = priorita,
                progetto = progetto,
                utenti = utenti,
                fileUri = fileUri
            )
            database.collection("Todo").document(id).set(updatedTodo).await()
        } catch (e: Exception) {
            // Gestisci l'errore se necessario
            throw Exception("Errore durante l'aggiornamento del Todo: ${e.message}")
        }
    }

    /**
     * Segna un'attività come completata o non completata.
     *
     * @param id L'ID dell'attività.
     * @param completato Lo stato di completamento dell'attività.
     */
    suspend fun completeTodo(id: String, completato: Boolean){
        database.collection("Todo")
            .document(id)
            .update("completato", !completato)
            .await()
    }

    /**
     * Aggiunge un utente a un'attività.
     *
     * @param id L'ID dell'attività.
     * @param newUser L'ID del nuovo utente da aggiungere.
     * @throws Exception Se si verifica un errore durante l'aggiornamento.
     */
    suspend fun addUserToTodo(id: String, newUser: String) {
        try {
            val document = database.collection("Todo").document(id).get().await()
            val leMieAttivita = document.toObject(LeMieAttivita::class.java)
            if (leMieAttivita != null) {
                val updatedUsers = leMieAttivita.utenti.toMutableList()
                updatedUsers.add(newUser)
                database.collection("Todo")
                    .document(id)
                    .update("utenti", updatedUsers)
                    .await()
            } else {
                throw Exception("Todo non trovato")
            }
        } catch (e: Exception) {
            // Gestisci l'errore se necessario
            throw Exception("Errore durante l'aggiornamento degli utenti del Todo: ${e.message}")
        }
    }

    /**
     * Rimuove un utente da un'attività.
     *
     * @param id L'ID dell'attività.
     * @param userToRemove L'ID dell'utente da rimuovere.
     * @throws Exception Se si verifica un errore durante l'aggiornamento.
     */
    suspend fun removeUserFromTodo(id: String, userToRemove: String) {
        try {
            val document = database.collection("Todo").document(id).get().await()
            val leMieAttivita = document.toObject(LeMieAttivita::class.java)
            if (leMieAttivita != null) {
                val updatedUsers = leMieAttivita.utenti.toMutableList()
                if (updatedUsers.remove(userToRemove)) {
                    database.collection("Todo")
                        .document(id)
                        .update("utenti", updatedUsers)
                        .await()
                } else {
                    throw Exception("Utente non trovato nella lista")
                }
            } else {
                throw Exception("Todo non trovato")
            }

            val task = getTodoById(id)

            if (task != null) {
                if(task.utenti.isEmpty()) {
                    deleteTodo(id)
                }
            }
        } catch (e: Exception) {
            // Gestisci l'errore se necessario
            throw Exception("Errore durante la rimozione dell'utente dal Todo: ${e.message}")
        }
    }

    /**
     * Recupera un'attività dato il suo ID.
     *
     * @param id L'ID dell'attività.
     * @return L'attività recuperata.
     * @throws Exception Se si verifica un errore durante il recupero.
     */
    suspend fun getTodoById(id: String): LeMieAttivita? {
        return try {
            val document = database.collection("Todo").document(id).get().await()
            document.toObject(LeMieAttivita::class.java)
        } catch (e: Exception) {
            // Gestisci l'errore se necessario
            throw Exception("Errore durante il recupero del Todo: ${e.message}")
        }
    }

    /**
     * Conta il numero di attività completate per un progetto specifico.
     *
     * @param progetto L'ID del progetto.
     * @return Il numero di attività completate.
     */
    suspend fun countCompletedTodo(progetto: String): Int {
        val snapshot = database.collection("Todo")
            .whereEqualTo("progetto", progetto)
            .whereEqualTo("completato", true)
            .get()
            .await()
        return snapshot.size()
    }

    /**
     * Conta il numero totale di attività per un progetto specifico.
     *
     * @param progetto L'ID del progetto.
     * @return Il numero totale di attività.
     */
    suspend fun countAllTodo(progetto: String): Int {
        val snapshot = database.collection("Todo")
            .whereEqualTo("progetto", progetto)
            .get()
            .await()
        return snapshot.size()
    }

    /**
     * Conta il numero di attività non completate per un progetto specifico.
     *
     * @param progettoId L'ID del progetto.
     * @return Il numero di attività non completate.
     */
    suspend fun countNonCompletedTodoByProject(progettoId: String) : Int {
        val snapshot = database.collection("Todo")
            .whereEqualTo("progetto", progettoId)
            .whereEqualTo("completato", false)
            .get()
            .await()
        return snapshot.size()
    }

}

