package com.example.teamsync.caratteristiche.notifiche.data.model

import java.util.Date

/**
 * Data class che rappresenta una notifica nell'applicazione.
 *
 * @property mittente L'ID dell'utente che ha inviato la notifica.
 * @property destinatario L'ID dell'utente che ha ricevuto la notifica.
 * @property tipo Il tipo di notifica (ad esempio, richiesta di amicizia, richiesta di progetto).
 * @property aperto Stato della notifica, se è stata aperta o meno.
 * @property contenuto Il contenuto della notifica.
 * @property id L'ID univoco della notifica.
 * @property progettoId L'ID del progetto associato alla notifica.
 * @property accettato Stato dell'accettazione della notifica, se è stata accettata o meno.
 * @property data La data e l'ora in cui la notifica è stata creata.
 */
data class Notifiche(
    var mittente: String = "",
    var destinatario: String = "",
    var tipo: String = "",
    var aperto : Boolean = false,
    var contenuto : String = "",
    var id : String = "",
    var progettoId : String = "",
    var accettato : String = "",
    val data: Date = Date()
)

