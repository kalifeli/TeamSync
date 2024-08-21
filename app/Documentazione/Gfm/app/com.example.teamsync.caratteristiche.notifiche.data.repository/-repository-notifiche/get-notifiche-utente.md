//[app](../../../index.md)/[com.example.teamsync.caratteristiche.notifiche.data.repository](../index.md)/[RepositoryNotifiche](index.md)/[getNotificheUtente](get-notifiche-utente.md)

# getNotificheUtente

[androidJvm]\
suspend fun [getNotificheUtente](get-notifiche-utente.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Notifiche](../../com.example.teamsync.caratteristiche.notifiche.data.model/-notifiche/index.md)&gt;

Recupera una lista di notifiche per un utente specifico da Firestore.

#### Return

Una lista di oggetti [Notifiche](../../com.example.teamsync.caratteristiche.notifiche.data.model/-notifiche/index.md) per l'utente specificato.     Restituisce una lista vuota se l'utente non ha notifiche o se si verifica un'eccezione.

#### Parameters

androidJvm

| | |
|---|---|
| userId | L'ID dell'utente per il quale si vogliono recuperare le notifiche.     Se `null`, viene restituita una lista vuota. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante il recupero delle notifiche da Firestore. |
