package com.example.teamsync.caratteristiche.impostazioni.data.model

import java.util.Date

/**
 * Rappresenta i termini e le condizioni o una policy all'interno delle impostazioni dell'applicazione.
 *
 * @property titolo Il titolo del documento o della sezione relativa ai termini e condizioni.
 * @property descrizione La descrizione o il contenuto dettagliato dei termini e condizioni.
 * @property data La data di pubblicazione o aggiornamento dei termini e condizioni. Può essere null se non disponibile.
 */
data class Terms(
    var titolo: String = "",
    var descrizione: String = "",
    var data: Date? = null,
)

