package com.example.teamsync.navigation

/**
 * Sealed class che rappresenta le diverse schermate (o route) dell'applicazione.
 *
 * Ogni oggetto all'interno della sealed class `Schermate` rappresenta una schermata specifica
 * con una route univoca utilizzata nel sistema di navigazione dell'applicazione. Questo permette
 * di definire in modo centralizzato tutte le schermate e le relative rotte, facilitando la gestione
 * della navigazione all'interno dell'app.
 *
 * @property route La stringa che rappresenta la route associata alla schermata.
 */
sealed class Schermate(val route: String){

    /**
     * Schermata di benvenuto.
     * Route: "schermata_benvenuto"
     */
    object Benvenuto : Schermate("schermata_benvenuto")

    /**
     * Schermata "I tuoi Progetti".
     * Route: "schermata_progetti"
     */
    object ItuoiProgetti: Schermate("schermata_progetti")

    /**
     * Schermata di Login.
     * Route: "Login"
     */
    object Login: Schermate("Login")

    /**
     * Schermata per il recupero della password.
     * Route: "schermata_RecuperoPassword"
     */
    object RecuperoPassword: Schermate("schermata_RecuperoPassword")

    /**
     * Schermata di registrazione.
     * Route: "Registrazione"
     */
    object Registrazione: Schermate("Registrazione")

    /**
     * Schermata per la verifica dell'email.
     * Route: "Verifica_Email"
     */
    object VerificaEmail : Schermate("Verifica_Email")

    /**
     * Schermata del profilo utente.
     * Route: "Profilo"
     */
    object Profilo: Schermate("Profilo")

    /**
     * Schermata delle notifiche.
     * Route: "Notifiche"
     */
    object Notifiche: Schermate("Notifiche")

    /**
     * Schermata per la modifica del profilo utente.
     * Route: "ModificaProfilo"
     */
    object ModificaProfilo: Schermate("ModificaProfilo")

    /**
     * Schermata delle impostazioni.
     * Route: "Impostazioni"
     */
    object Impostazioni: Schermate ("Impostazioni")

    /**
     * Schermata per la gestione del tema.
     * Route: "tema"
     */
    object Tema: Schermate("tema")

    /**
     * Schermata dei termini e condizioni.
     * Route: "terms"
     */
    object Terms: Schermate("terms")

    /**
     * Schermata di supporto.
     * Route: "supporto"
     */
    object Supporto: Schermate("supporto")

    /**
     * Schermata per la gestione delle notifiche.
     * Route: "Impostazioni_Notifiche"
     */
    object ImpNotifche: Schermate("Impostazioni_Notifiche")

    /**
     * Schermata per la gestione dei task.
     * Route: "Impostazioni_Task"
     */
    object Imptask: Schermate("Impostazioni_Task")

    /**
     * Schermata per la gestione dei progetti.
     * Route: "Impostazioni_Progetti"
     */
    object ImpoProgetti: Schermate("Impostazioni_Progetti")

}
