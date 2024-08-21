//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)/[getPartecipantiDelProgetto](get-partecipanti-del-progetto.md)

# getPartecipantiDelProgetto

[androidJvm]\
suspend fun [getPartecipantiDelProgetto](get-partecipanti-del-progetto.md)(progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;

Recupera la lista dei partecipanti di un progetto.

#### Return

Una lista di ID degli utenti partecipanti al progetto.

#### Parameters

androidJvm

| | |
|---|---|
| progettoId | L'ID del progetto di cui si vogliono recuperare i partecipanti. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante l'operazione. |
