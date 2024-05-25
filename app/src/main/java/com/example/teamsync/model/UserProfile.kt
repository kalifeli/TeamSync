package com.example.teamsync.model
import java.util.*

data class UserProfile(
    val id: String,
    var nome: String,
    var cognome: String,
    var username: String,
    var dataDiNascita: Date,
    var matricola: String,
    var email: String
)
