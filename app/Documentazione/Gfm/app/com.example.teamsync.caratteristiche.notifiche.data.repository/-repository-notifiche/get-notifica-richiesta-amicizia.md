//[app](../../../index.md)/[com.example.teamsync.caratteristiche.notifiche.data.repository](../index.md)/[RepositoryNotifiche](index.md)/[getNotificaRichiestaAmicizia](get-notifica-richiesta-amicizia.md)

# getNotificaRichiestaAmicizia

[androidJvm]\
suspend fun [getNotificaRichiestaAmicizia](get-notifica-richiesta-amicizia.md)(mittenteId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinatarioId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Notifiche](../../com.example.teamsync.caratteristiche.notifiche.data.model/-notifiche/index.md)?

Recupera una notifica di richiesta di amicizia specifica tra un mittente e un destinatario.

#### Return

Un oggetto [Notifiche](../../com.example.teamsync.caratteristiche.notifiche.data.model/-notifiche/index.md) se la richiesta di amicizia esiste, altrimenti null.

#### Parameters

androidJvm

| | |
|---|---|
| mittenteId | L'ID dell'utente che ha inviato la richiesta di amicizia. |
| destinatarioId | L'ID dell'utente che ha ricevuto la richiesta di amicizia. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante il recupero della notifica da Firestore. |
