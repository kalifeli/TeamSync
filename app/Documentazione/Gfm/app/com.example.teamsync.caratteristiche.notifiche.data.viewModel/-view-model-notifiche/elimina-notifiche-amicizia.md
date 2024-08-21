//[app](../../../index.md)/[com.example.teamsync.caratteristiche.notifiche.data.viewModel](../index.md)/[ViewModelNotifiche](index.md)/[eliminaNotificheAmicizia](elimina-notifiche-amicizia.md)

# eliminaNotificheAmicizia

[androidJvm]\
fun [eliminaNotificheAmicizia](elimina-notifiche-amicizia.md)(userId1: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), userId2: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Elimina tutte le notifiche di amicizia tra due utenti specificati.

Questa funzione avvia una coroutine per eseguire l'eliminazione delle notifiche di amicizia tra due utenti identificati da `userId1` e `userId2`. Viene utilizzato il repository delle notifiche per effettuare l'operazione di eliminazione. In caso di errore durante l'operazione, l'errore viene loggato.

#### Parameters

androidJvm

| | |
|---|---|
| userId1 | L'ID del primo utente coinvolto nell'amicizia. |
| userId2 | L'ID del secondo utente coinvolto nell'amicizia. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante l'eliminazione delle notifiche di amicizia, viene loggato. |
