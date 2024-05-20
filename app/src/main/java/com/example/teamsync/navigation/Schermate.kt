package com.example.teamsync.navigation

sealed class Schermate(val route: String){
    object Accesso : Schermate(route = "schermata_accesso")
    object Iscrizione : Schermate(route = "schermata_iscrizione")
    object Benvenuto : Schermate("schermata_benvenuto")
    object Progetti: Schermate("schermata_progetti")

}
