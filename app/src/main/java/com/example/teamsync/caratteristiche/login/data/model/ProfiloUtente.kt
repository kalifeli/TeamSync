package com.example.teamsync.caratteristiche.login.data.model

import java.util.Date


data class ProfiloUtente(
    var id: String = "",
    var nome: String = "",
    var cognome: String = "",
    var matricola: String = "",
    var dataDiNascita: Date = Date(),
    var sesso: SessoUtente = SessoUtente.UOMO,
    var email: String = "",
    var primoAccesso: Boolean = true,
    val immagine: String? = null,
    var amici: List<String> = emptyList()
)
enum class SessoUtente{
    UOMO, DONNA, ALTRO
}

