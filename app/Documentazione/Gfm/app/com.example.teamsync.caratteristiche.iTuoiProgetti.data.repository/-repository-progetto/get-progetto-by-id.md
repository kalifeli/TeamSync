//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)/[getProgettoById](get-progetto-by-id.md)

# getProgettoById

[androidJvm]\
suspend fun [getProgettoById](get-progetto-by-id.md)(progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Progetto](../../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md)?

Recupera un progetto dato il suo ID.

#### Return

L'oggetto `Progetto` corrispondente all'ID specificato, oppure `null` se non viene trovato alcun progetto.

#### Parameters

androidJvm

| | |
|---|---|
| progettoId | L'ID del progetto da recuperare. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante l'operazione. |
