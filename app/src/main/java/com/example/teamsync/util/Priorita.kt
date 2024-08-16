package com.example.teamsync.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.teamsync.R
import com.example.teamsync.util.theme.ColorePrioritaAlta
import com.example.teamsync.util.theme.ColorePrioritaBassa
import com.example.teamsync.util.theme.ColorePrioritaMedia
import com.example.teamsync.util.theme.ColoreSenzaPriorita

/**
 * Enum class che rappresenta i diversi livelli di priorità per le attività o i task.
 * Ogni livello di priorità è associato a un colore specifico che può essere utilizzato
 * per visualizzare graficamente l'importanza di un task.
 *
 * @property colore Il colore associato a ciascun livello di priorità.
 */
enum class Priorita(val colore: Color, val nomeResId: Int) {
    /**
     * Priorità alta, rappresentata dal colore associato a un livello di urgenza elevato.
     */
    ALTA(ColorePrioritaAlta, R.string.PrioritaALTA ),

    /**
     * Priorità media, rappresentata dal colore associato a un livello di urgenza moderato.
     */
    MEDIA(ColorePrioritaMedia, R.string.PrioritaMedia),

    /**
     * Priorità bassa, rappresentata dal colore associato a un livello di urgenza basso.
     */
    BASSA(ColorePrioritaBassa, R.string.PrioritaBassa),

    /**
     * Nessuna priorità, rappresentata dal colore associato ad un'attività o progetto senza urgenza specifica.
     */
    NESSUNA(ColoreSenzaPriorita, R.string.NessunaPriorita);

    /**
     * Funzione per ottenere il nome tradotto della priorità
     */
    @Composable
    fun nomeTradotto(): String {
        val context = LocalContext.current
        return context.getString(nomeResId)
    }
}