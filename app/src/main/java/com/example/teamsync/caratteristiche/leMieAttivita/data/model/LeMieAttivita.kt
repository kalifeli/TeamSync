package com.example.teamsync.caratteristiche.leMieAttivita.data.model

import com.example.teamsync.util.Priorita
import com.google.firebase.firestore.DocumentId
import java.util.Date

/**
 * Rappresenta una singola attività all'interno del progetto.
 *
 * @property id L'ID univoco dell'attività (generato automaticamente da Firestore).
 * @property titolo Il titolo dell'attività.
 * @property descrizione La descrizione dettagliata dell'attività.
 * @property dataScadenza La data di scadenza dell'attività.
 * @property priorita La priorità dell'attività (può essere NESSUNA, BASSA, MEDIA, ALTA).
 * @property completato Indica se l'attività è stata completata.
 * @property fileUri L'URI del file allegato all'attività (se presente).
 * @property progetto L'ID del progetto a cui appartiene l'attività.
 * @property dataCreazione La data di creazione dell'attività.
 * @property utenti La lista degli ID degli utenti assegnati all'attività.
 */
data class LeMieAttivita(
    @DocumentId val id: String? = null,
    val titolo: String = "",
    val descrizione: String = "",
    val dataScadenza: Date = Date(),
    val priorita: Priorita = Priorita.NESSUNA,
    val completato: Boolean = false,
    val fileUri: String? = null,
    val progetto: String = "",
    val dataCreazione: Date = Date(),
    val utenti: List<String> = emptyList()
)


