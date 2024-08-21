//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)/[aggiungiPartecipante](aggiungi-partecipante.md)

# aggiungiPartecipante

[androidJvm]\
suspend fun [aggiungiPartecipante](aggiungi-partecipante.md)(progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)

Funzione che permette di aggiungere un partecipante a un progetto.

#### Parameters

androidJvm

| | |
|---|---|
| progettoId | L'ID del progetto a cui si vuole aggiungere il partecipante. Può essere null, ma in tal caso la funzione non farà nulla. |
| userId | L'ID dell'utente che si vuole aggiungere come partecipante al progetto. Può essere null, ma in tal caso la funzione non farà nulla.<br>La funzione utilizza Firestore per aggiungere l'utente alla lista dei partecipanti del progetto specificato. Se uno dei parametri è null, il documento del progetto non sarà aggiornato. La funzione è una funzione sospesa e deve essere chiamata all'interno di una coroutine o di un'altra funzione sospesa. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante l'aggiornamento del documento su Firestore, l'eccezione sarà rilanciata. |
