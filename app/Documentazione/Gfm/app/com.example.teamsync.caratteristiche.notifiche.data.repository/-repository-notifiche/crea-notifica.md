//[app](../../../index.md)/[com.example.teamsync.caratteristiche.notifiche.data.repository](../index.md)/[RepositoryNotifiche](index.md)/[creaNotifica](crea-notifica.md)

# creaNotifica

[androidJvm]\
fun [creaNotifica](crea-notifica.md)(mittenteId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinatarioId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tipo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), contenuto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Crea una nuova notifica nella collezione &quot;Notifiche&quot; di Firestore.

#### Parameters

androidJvm

| | |
|---|---|
| mittenteId | L'ID dell'utente che invia la notifica. |
| destinatarioId | L'ID dell'utente che riceve la notifica. |
| tipo | Il tipo di notifica. |
| contenuto | Il contenuto della notifica. |
| progetto | L'ID del progetto a cui si riferisce la notifica. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante la creazione della notifica. |
