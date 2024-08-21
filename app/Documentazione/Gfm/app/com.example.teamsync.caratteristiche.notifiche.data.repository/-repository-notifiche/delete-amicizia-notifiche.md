//[app](../../../index.md)/[com.example.teamsync.caratteristiche.notifiche.data.repository](../index.md)/[RepositoryNotifiche](index.md)/[deleteAmiciziaNotifiche](delete-amicizia-notifiche.md)

# deleteAmiciziaNotifiche

[androidJvm]\
suspend fun [deleteAmiciziaNotifiche](delete-amicizia-notifiche.md)(mittenteId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinatarioId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Elimina tutte le notifiche di amicizia tra un mittente e un destinatario specifici. Le notifiche di amicizia possono includere richieste di amicizia e accettazioni di amicizia.

#### Parameters

androidJvm

| | |
|---|---|
| mittenteId | L'ID dell'utente che ha inviato la richiesta o l'accettazione di amicizia. |
| destinatarioId | L'ID dell'utente che ha ricevuto la richiesta o l'accettazione di amicizia. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante l'eliminazione delle notifiche da Firestore. |
