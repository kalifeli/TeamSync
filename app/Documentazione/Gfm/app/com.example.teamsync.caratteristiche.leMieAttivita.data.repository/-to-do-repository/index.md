//[app](../../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.data.repository](../index.md)/[ToDoRepository](index.md)

# ToDoRepository

[androidJvm]\
class [ToDoRepository](index.md)

Repository per gestire le operazioni relative alle attività.

## Constructors

| | |
|---|---|
| [ToDoRepository](-to-do-repository.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [addTodo](add-todo.md) | [androidJvm]<br>suspend fun [addTodo](add-todo.md)(titolo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), descrizione: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dataScad: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html), priorita: [Priorita](../../com.example.teamsync.util/-priorita/index.md), utenti: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Aggiunge una nuova attività al database. |
| [addUserToTodo](add-user-to-todo.md) | [androidJvm]<br>suspend fun [addUserToTodo](add-user-to-todo.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), newUser: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Aggiunge un utente a un'attività. |
| [completeTodo](complete-todo.md) | [androidJvm]<br>suspend fun [completeTodo](complete-todo.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), completato: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Segna un'attività come completata o non completata. |
| [countAllTodo](count-all-todo.md) | [androidJvm]<br>suspend fun [countAllTodo](count-all-todo.md)(progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Conta il numero totale di attività per un progetto specifico. |
| [countCompletedTodo](count-completed-todo.md) | [androidJvm]<br>suspend fun [countCompletedTodo](count-completed-todo.md)(progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Conta il numero di attività completate per un progetto specifico. |
| [countNonCompletedTodoByProject](count-non-completed-todo-by-project.md) | [androidJvm]<br>suspend fun [countNonCompletedTodoByProject](count-non-completed-todo-by-project.md)(progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Conta il numero di attività non completate per un progetto specifico. |
| [deleteTodo](delete-todo.md) | [androidJvm]<br>suspend fun [deleteTodo](delete-todo.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Elimina un'attività dal database. |
| [getAllTodoCompletate](get-all-todo-completate.md) | [androidJvm]<br>suspend fun [getAllTodoCompletate](get-all-todo-completate.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LeMieAttivita](../../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md)&gt;<br>Recupera tutte le attività completate dal database. |
| [getNotCompletedTodo](get-not-completed-todo.md) | [androidJvm]<br>suspend fun [getNotCompletedTodo](get-not-completed-todo.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LeMieAttivita](../../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md)&gt;<br>Recupera tutte le attività non completate dal database. |
| [getTodoById](get-todo-by-id.md) | [androidJvm]<br>suspend fun [getTodoById](get-todo-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [LeMieAttivita](../../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md)?<br>Recupera un'attività dato il suo ID. |
| [getTodoByUtente](get-todo-by-utente.md) | [androidJvm]<br>suspend fun [getTodoByUtente](get-todo-by-utente.md)(idProg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), utenteId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LeMieAttivita](../../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md)&gt;<br>Recupera tutte le attività di un utente specifico per un dato progetto. |
| [removeUserFromTodo](remove-user-from-todo.md) | [androidJvm]<br>suspend fun [removeUserFromTodo](remove-user-from-todo.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), userToRemove: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Rimuove un utente da un'attività. |
| [updateTodo](update-todo.md) | [androidJvm]<br>suspend fun [updateTodo](update-todo.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), titolo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), descrizione: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dataScad: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html), priorita: [Priorita](../../com.example.teamsync.util/-priorita/index.md), progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), utenti: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, fileUri: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Aggiorna un'attività nel database. |
