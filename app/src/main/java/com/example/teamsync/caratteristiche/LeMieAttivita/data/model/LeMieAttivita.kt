package com.example.teamsync.caratteristiche.LeMieAttivita.data.model

import com.example.teamsync.data.models.Priorità
import java.util.Calendar
import java.util.Date


data class LeMieAttivita(
    var taskId: Int,
    var titolo: String,
    var descrizione: String,
    var dataScadenza: Date,
    var priorita: Priorità
)

fun getFakeTodo(): List<LeMieAttivita>{
    return listOf<LeMieAttivita>(
        LeMieAttivita(1, "Prima", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time, Priorità.ALTA ),
        LeMieAttivita(2, "Seconda", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time, Priorità.BASSA ),
        LeMieAttivita(3, "Terza", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time, Priorità.MEDIA ),
        LeMieAttivita(4, "Quarta", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time, Priorità.MEDIA ),
        LeMieAttivita(5, "Sesta", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time, Priorità.ALTA ),
        LeMieAttivita(6, "Seconda", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time, Priorità.BASSA ),
        LeMieAttivita(7, "Terza", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time, Priorità.MEDIA ),
        LeMieAttivita(8, "Quarta", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time, Priorità.MEDIA ),
        LeMieAttivita(9, "Sesta", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time, Priorità.ALTA ),
        )
}
