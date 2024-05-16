package com.example.teamsync.navigation

sealed class Schermate(val route: String){
    data object Progetti: Schermate("Progetti")
    object Benvenuto : Schermate("schermata_benvenuto")
}
