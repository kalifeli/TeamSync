//[app](../../../index.md)/[com.example.teamsync.caratteristiche.notifiche.data.repository](../index.md)/[RepositoryNotifiche](index.md)

# RepositoryNotifiche

[androidJvm]\
class [RepositoryNotifiche](index.md)

Repository per gestire le operazioni di CRUD sulle notifiche.

## Constructors

| | |
|---|---|
| [RepositoryNotifiche](-repository-notifiche.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [apriNotifica](apri-notifica.md) | [androidJvm]<br>suspend fun [apriNotifica](apri-notifica.md)(notificaId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Segna una notifica come aperta. |
| [creaNotifica](crea-notifica.md) | [androidJvm]<br>fun [creaNotifica](crea-notifica.md)(mittenteId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinatarioId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tipo: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), contenuto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Crea una nuova notifica nella collezione &quot;Notifiche&quot; di Firestore. |
| [deleteAllNotifiche](delete-all-notifiche.md) | [androidJvm]<br>suspend fun [deleteAllNotifiche](delete-all-notifiche.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Elimina tutte le notifiche di un utente specifico dalla collezione &quot;Notifiche&quot; di Firestore. |
| [deleteAmiciziaNotifiche](delete-amicizia-notifiche.md) | [androidJvm]<br>suspend fun [deleteAmiciziaNotifiche](delete-amicizia-notifiche.md)(mittenteId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinatarioId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Elimina tutte le notifiche di amicizia tra un mittente e un destinatario specifici. Le notifiche di amicizia possono includere richieste di amicizia e accettazioni di amicizia. |
| [deleteNotifica](delete-notifica.md) | [androidJvm]<br>suspend fun [deleteNotifica](delete-notifica.md)(notificaId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Elimina una notifica specifica dalla collezione &quot;Notifiche&quot; di Firestore. |
| [getNotificaIdByContent](get-notifica-id-by-content.md) | [androidJvm]<br>suspend fun [getNotificaIdByContent](get-notifica-id-by-content.md)(contenuto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Recupera l'ID di una notifica in base al contenuto specificato. |
| [getNotificaRichiestaAmicizia](get-notifica-richiesta-amicizia.md) | [androidJvm]<br>suspend fun [getNotificaRichiestaAmicizia](get-notifica-richiesta-amicizia.md)(mittenteId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), destinatarioId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Notifiche](../../com.example.teamsync.caratteristiche.notifiche.data.model/-notifiche/index.md)?<br>Recupera una notifica di richiesta di amicizia specifica tra un mittente e un destinatario. |
| [getNotificheUtente](get-notifiche-utente.md) | [androidJvm]<br>suspend fun [getNotificheUtente](get-notifiche-utente.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Notifiche](../../com.example.teamsync.caratteristiche.notifiche.data.model/-notifiche/index.md)&gt;<br>Recupera una lista di notifiche per un utente specifico da Firestore. |
| [updateNotifica](update-notifica.md) | [androidJvm]<br>suspend fun [updateNotifica](update-notifica.md)(notifica: [Notifiche](../../com.example.teamsync.caratteristiche.notifiche.data.model/-notifiche/index.md))<br>Aggiorna una notifica esistente nella collezione &quot;Notifiche&quot; di Firestore. |
