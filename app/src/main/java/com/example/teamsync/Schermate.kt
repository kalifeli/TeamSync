package com.example.teamsync

sealed class Schermate(val screens: String){
    data object Progetti: Schermate("Progetti")
}
