//[app](../../../index.md)/[com.example.teamsync.caratteristiche.notifiche.data.repository](../index.md)/[RepositoryNotifiche](index.md)/[getNotificaIdByContent](get-notifica-id-by-content.md)

# getNotificaIdByContent

[androidJvm]\
suspend fun [getNotificaIdByContent](get-notifica-id-by-content.md)(contenuto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?

Recupera l'ID di una notifica in base al contenuto specificato.

#### Return

L'ID della notifica se trovata, altrimenti null.

#### Parameters

androidJvm

| | |
|---|---|
| contenuto | Il contenuto della notifica da cercare. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante il recupero della notifica. |
