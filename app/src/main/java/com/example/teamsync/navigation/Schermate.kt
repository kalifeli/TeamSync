package com.example.teamsync.navigation

sealed class Schermate(val route: String){
    object Progetti: Schermate("schermata_progetti")
    object Benvenuto : Schermate("schermata_benvenuto")
}
