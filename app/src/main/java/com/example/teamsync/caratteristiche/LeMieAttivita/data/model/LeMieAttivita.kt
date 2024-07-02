package com.example.teamsync.caratteristiche.LeMieAttivita.data.model

import com.example.teamsync.data.models.Priorità
import com.google.firebase.firestore.DocumentId
import java.util.Calendar
import java.util.Date


data class LeMieAttivita(

    @DocumentId val id: String? = null, // Campo per l'ID del documento
    val titolo: String = "",
    val descrizione: String = "",
    val dataScadenza: Date = Date(),  // Data di scadenza
    val priorita: Priorità = Priorità.NESSUNA // Priorità dell'attività
) {
    // Costruttore vuoto richiesto da Firestore per deserializzare l'oggetto
    constructor() : this(null, "", "", Date(), Priorità.NESSUNA)
}


