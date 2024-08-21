//[app](../../../index.md)/[com.example.teamsync.caratteristiche.autentificazione.data.viewModel](../index.md)/[ViewModelUtente](index.md)/[getUserProfileById](get-user-profile-by-id.md)

# getUserProfileById

[androidJvm]\
suspend fun [getUserProfileById](get-user-profile-by-id.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ProfiloUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md)?

Recupera il profilo dell'utente corrispondente all'ID specificato.

Questa funzione utilizza il repository per ottenere il profilo utente dal database. Imposta `_isLoading` su `true` all'inizio e lo reimposta su `false` alla fine dell'operazione, indipendentemente dal fatto che abbia avuto successo o meno.

#### Return

Un oggetto [ProfiloUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md) se il profilo è stato trovato, o `null` se si è verificato un errore o se il profilo non esiste.

#### Parameters

androidJvm

| | |
|---|---|
| userId | L'ID dell'utente di cui si vuole ottenere il profilo. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un problema durante il recupero del profilo utente. |
