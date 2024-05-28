package com.example.teamsync.data.models

import javax.inject.Inject

data class LeMieAttivita(
    val taskId: Int = 0,
    val titolo: String,
    val descrizione: String,
    val priorita: Priorità
)