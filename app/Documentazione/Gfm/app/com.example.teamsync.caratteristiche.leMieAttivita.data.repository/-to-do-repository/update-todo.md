//[app](../../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.data.repository](../index.md)/[ToDoRepository](index.md)/[updateTodo](update-todo.md)

# updateTodo

[androidJvm]\
suspend fun [updateTodo](update-todo.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), titolo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), descrizione: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dataScad: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html), priorita: [Priorita](../../com.example.teamsync.util/-priorita/index.md), progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), utenti: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, fileUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)

Aggiorna un'attività nel database.

#### Parameters

androidJvm

| | |
|---|---|
| id | L'ID dell'attività da aggiornare. |
| titolo | Il nuovo titolo dell'attività. |
| descrizione | La nuova descrizione dell'attività. |
| dataScad | La nuova data di scadenza dell'attività. |
| priorita | La nuova priorità dell'attività. |
| progetto | L'ID del progetto associato all'attività. |
| utenti | La lista degli ID degli utenti assegnati all'attività. |
| fileUri | L'URI del file associato all'attività. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante l'aggiornamento. |
