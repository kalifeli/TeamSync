package com.example.teamsync.caratteristiche.iTuoiProgetti.data.model

import com.example.teamsync.caratteristiche.leMieAttivita.data.model.LeMieAttivita
import com.example.teamsync.util.Priorita
import com.google.firebase.firestore.DocumentId
import java.util.Date

/**
 * Rappresenta un progetto in un sistema di gestione progetti.
 *
 * @property id L'ID univoco del progetto, che corrisponde all'ID del documento in Firestore.
 * @property nome Il nome del progetto.
 * @property descrizione Una descrizione opzionale del progetto.
 * @property dataCreazione La data di creazione del progetto. Impostata automaticamente alla data corrente se non specificata.
 * @property dataScadenza La data di scadenza del progetto. Impostata automaticamente alla data di scadenza del progetto se non specificata.
 * @property dataConsegna La data di consegna del progetto.
 * @property priorita La priorità del progetto, che può essere `NESSUNA`, `BASSA`, `MEDIA`, `ALTA`, ecc.
 * @property partecipanti Una lista di ID degli utenti partecipanti al progetto.
 * @property voto Il voto assegnato al progetto, che può essere aggiornato al termine del progetto. Il valore predefinito è "Non Valutato".
 * @property completato Indica se il progetto è stato completato. Il valore predefinito è `false`.
 * @property codice Un codice univoco associato al progetto, utilizzato per identificare il progetto in modo univoco tra i partecipanti.
 */
data class Progetto(
    @DocumentId val id: String? = null,
    val nome: String = "",
    val descrizione: String? = "",
    val dataCreazione: Date = Date(),
    val dataScadenza: Date = Date(),
    val dataConsegna: Date = dataScadenza,
    val priorita: Priorita = Priorita.NESSUNA,
    val partecipanti: List<String> = emptyList(),
    val voto: String = "Non Valutato",
    val completato: Boolean = false,
    val codice: String = ""
)

