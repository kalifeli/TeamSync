package com.example.teamsync.navigation

sealed class Schermate(val route: String){

    object Benvenuto : Schermate("schermata_benvenuto")
    object ItuoiProgetti: Schermate("schermata_progetti")

    object Login: Schermate("Login")
    object RecuperoPassword: Schermate("schermata_RecuperoPassword")

    object Registrazione: Schermate("Registrazione")
    object VerificaEmail : Schermate("Verifica_Email")
    object Inizio: Schermate("Start")
    object ModificaProfilo: Schermate("ModificaProfilo")
    object Impostazioni: Schermate ("Impostazioni")
    object Tema: Schermate("tema")
    object Terms: Schermate("terms")
    object Supporto: Schermate("supporto")
    object LeMieAttivita: Schermate("LeMieAttivita")

}
