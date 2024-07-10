package com.example.teamsync.caratteristiche.Notifiche.data.repository

import com.example.teamsync.caratteristiche.Notifiche.data.model.Notifiche
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.util.UUID



class RepositoryNotifiche {


    suspend fun getNotifiche(): List<Notifiche> {
        val db = FirebaseFirestore.getInstance()
        val notificheList = mutableListOf<Notifiche>()

        try {
            val querySnapshot = db.collection("Notifiche").get().await()
            for (document in querySnapshot.documents) {
                document.toObject<Notifiche>()?.let { notifiche ->
                    notificheList.add(notifiche)
                    println("Notifica ricevuta: $notifiche")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return notificheList
    }

    private val firestore: FirebaseFirestore = Firebase.firestore


    fun creaNotifica(mittenteId: String, destinatarioId: String, tipo: String, contenuto: String, progetto: String) {
        try {
            val notificaId = UUID.randomUUID().toString()
            val timestamp = System.currentTimeMillis()
            var notifica = Notifiche ()
            if(tipo == "Richiesta_Amicizia" || tipo == "Richiesta_Progetto")
            {
                notifica = Notifiche(mittenteId, destinatarioId, tipo, false, contenuto,notificaId,progetto,"false")
            }
            else
            {
                notifica = Notifiche(mittenteId, destinatarioId, tipo, false, contenuto,notificaId,progetto)

            }

            firestore.collection("Notifiche").document(notificaId)
                .set(notifica)
                .addOnSuccessListener {
                    println("Notifica inserita con successo con ID: $notificaId")
                }
                .addOnFailureListener { e ->
                    println("Errore durante l'inserimento della notifica: $e")
                    throw e // Assicurati di lanciare l'eccezione per propagarla correttamente
                }

        } catch (e: Exception) {
            println("Eccezione durante la creazione della notifica: $e")
            throw e
        }
    }

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
            println("Errore durante l'aggiornamento della notifica: $e")
            throw e
        }
    }

    suspend fun updateNotifica(notifica: Notifiche) {
        try {
            val notificaRef = firestore.collection("Notifiche").document(notifica.id)
            notificaRef.set(notifica).await()
            println("Notifica aggiornata con successo su Firebase: ${notifica.id}")
        } catch (e: Exception) {
            println("Errore durante l'aggiornamento della notifica: $e")
            throw e
        }
    }




}


