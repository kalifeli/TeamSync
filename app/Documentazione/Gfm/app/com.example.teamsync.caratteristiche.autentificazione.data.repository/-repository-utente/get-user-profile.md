//[app](../../../index.md)/[com.example.teamsync.caratteristiche.autentificazione.data.repository](../index.md)/[RepositoryUtente](index.md)/[getUserProfile](get-user-profile.md)

# getUserProfile

[androidJvm]\
suspend fun [getUserProfile](get-user-profile.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ProfiloUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md)?

Ottiene il profilo dell'utente con l'ID fornito.

#### Return

Un [ProfiloUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md) se il profilo è trovato, null altrimenti.

#### Parameters

androidJvm

| | |
|---|---|
| userId | L'ID dell'utente. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | se il recupero del profilo fallisce. |
