package com.example.teamsync.data.models
/*
data class LeMieAttivita(
    val taskId: Int = 0,
    val titolo: String,
    val descrizione: String,
    val priorita: Priorità
)*/
import java.util.Date
import java.util.Calendar

data class Todo(
    var id: Int,
    var titolo: String,
    var desc: String,
    var dataScadenza : Date,
)

fun getFakeTodo () : List<Todo> {
    return listOf<Todo>(
        Todo(1, "First","Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit.." ,Calendar.getInstance().time),
        Todo(2, "Second", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time),
        Todo(3, "Third","Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit.." ,Calendar.getInstance().time),
        Todo(4, "Forth", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time),
        Todo(5, "First","Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit.." ,Calendar.getInstance().time),
        Todo(6, "Second", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time),
        Todo(7, "Third","Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit.." ,Calendar.getInstance().time),
        Todo(8, "Forth", "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..",Calendar.getInstance().time),

        )
}

