//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)/[abbandonaProgetto](abbandona-progetto.md)

# abbandonaProgetto

[androidJvm]\
suspend fun [abbandonaProgetto](abbandona-progetto.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), callback: ([Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))

Funzione che permette a un utente di abbandonare un progetto.

#### Parameters

androidJvm

| | |
|---|---|
| userId | L'ID dell'utente che vuole abbandonare il progetto. Può essere null, ma in tal caso la funzione non farà nulla. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante l'aggiornamento del documento su Firestore, l'eccezione sarà rilanciata. |
