package com.example.teamsync.caratteristiche.login.data.model



data class ProfiloUtente(
    var id: String = "",
    var nome: String = "",
    var cognome: String = "",
    var matricola: String = "",
    var dataDiNascita: String = "",
    var email: String = "",
    var primoAccesso: Boolean = true,
    val immagine: String? = null,
    var amici: List<String> = emptyList()
)

