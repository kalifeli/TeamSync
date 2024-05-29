package com.example.teamsync.caratteristiche.login.data.model

data class ProfiloUtente(
    val id: String,
    var nome: String,
    var cognome: String,
    var matricola: String,
    var dataDiNascita: String, // TODO cambia tipo in Date
    var email: String
)
