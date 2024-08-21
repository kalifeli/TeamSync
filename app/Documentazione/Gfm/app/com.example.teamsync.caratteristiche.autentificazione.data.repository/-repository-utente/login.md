//[app](../../../index.md)/[com.example.teamsync.caratteristiche.autentificazione.data.repository](../index.md)/[RepositoryUtente](index.md)/[login](login.md)

# login

[androidJvm]\
suspend fun [login](login.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): FirebaseUser?

Effettua il login di un utente con l'email e la password fornite.

#### Return

Un FirebaseUser se il login ha successo, null altrimenti.

#### Parameters

androidJvm

| | |
|---|---|
| email | L'indirizzo email dell'utente. |
| password | La password dell'utente. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | se il login fallisce. |
