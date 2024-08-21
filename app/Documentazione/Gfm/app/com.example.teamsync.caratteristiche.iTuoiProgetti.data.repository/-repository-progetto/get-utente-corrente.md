//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)/[getUtenteCorrente](get-utente-corrente.md)

# getUtenteCorrente

[androidJvm]\
fun [getUtenteCorrente](get-utente-corrente.md)(): FirebaseUser?

Funzione che recupera l'utente attualmente autenticato.

#### Return

Un oggetto `FirebaseUser` che rappresenta l'utente attualmente autenticato, oppure `null` se non c'è alcun utente autenticato.

La funzione utilizza Firebase Authentication per ottenere l'utente attualmente autenticato tramite la proprietà `currentUser`. Se l'operazione ha successo, l'utente attualmente autenticato viene restituito. In caso di eccezione, l'eccezione viene rilanciata.

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante il recupero dell'utente attualmente autenticato, l'eccezione sarà rilanciata. |
