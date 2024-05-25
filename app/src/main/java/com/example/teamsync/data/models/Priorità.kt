package com.example.teamsync.data.models

import androidx.compose.ui.graphics.Color
import com.example.teamsync.ui.theme.ColorePrioritaAlta
import com.example.teamsync.ui.theme.ColorePrioritaBassa
import com.example.teamsync.ui.theme.ColorePrioritaMedia
import com.example.teamsync.ui.theme.ColoreSenzaPriorita

enum class Priorità(val colore: Color) {
    ALTA(ColorePrioritaAlta),
    MEDIA(ColorePrioritaMedia),
    BASSA(ColorePrioritaBassa),
    NESSUNA(ColoreSenzaPriorita)

}