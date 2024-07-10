package com.example.teamsync.caratteristiche.Notifiche.data.model



data class Notifiche(
    var mittente: String = "",
    var destinatario: String = "",
    var Tipo: String = "",
    var aperto : Boolean = false,
    var Contenuto : String = "",
    var id : String = "",
    var progetto_id : String = "",
    var accettato : String = "",

    )
