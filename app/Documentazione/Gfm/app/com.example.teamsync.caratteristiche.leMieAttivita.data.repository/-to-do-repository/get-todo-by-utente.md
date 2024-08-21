//[app](../../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.data.repository](../index.md)/[ToDoRepository](index.md)/[getTodoByUtente](get-todo-by-utente.md)

# getTodoByUtente

[androidJvm]\
suspend fun [getTodoByUtente](get-todo-by-utente.md)(idProg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), utenteId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LeMieAttivita](../../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md)&gt;

Recupera tutte le attività di un utente specifico per un dato progetto.

#### Return

Una lista di attività associate all'utente per il progetto specificato.

#### Parameters

androidJvm

| | |
|---|---|
| idProg | L'ID del progetto. |
| utenteId | L'ID dell'utente. |
