package com.example.teamsync.caratteristiche.iTuoiProgetti.data.model

import com.example.teamsync.caratteristiche.LeMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.data.models.Priorità
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Progetto(
    @DocumentId val id: String? = null, // L'ID del documento Firestore
    val nome: String = "",
    val descrizione: String? = "",
    val dataCreazione: Date = Date(), // Data di creazione del progetto
    val dataScadenza: Date = Date(), // Data di scadenza del progetto (può essere null)
    val priorita: Priorità = Priorità.NESSUNA, // Priorità del progetto
    val attivita: List<LeMieAttivita> = emptyList(), // Lista di attività associate al progetto
    val partecipanti: List<String> = emptyList(), // lista degli id dei partecipanti
    val completato: Boolean = false,
    val codice: String = ""
)