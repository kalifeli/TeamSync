package com.example.teamsync.caratteristiche.impostazioni.data.model


/**
 * Rappresenta una FAQ (domanda frequente) nel contesto delle impostazioni dell'applicazione.
 *
 * @property domanda La domanda che viene frequentemente posta dagli utenti.
 * @property risposta La risposta alla domanda frequente.
 * @property sezione La sezione o categoria della FAQ, utilizzata per organizzare le FAQ in gruppi tematici.
 */
data class Faq(
    var domanda: String = "",
    var risposta: String = "",
    var sezione : String = "",
)
