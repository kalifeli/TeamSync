//[app](../../../index.md)/[com.example.teamsync.caratteristiche.autentificazione.data.repository](../index.md)/[RepositoryUtente](index.md)/[signUp](sign-up.md)

# signUp

[androidJvm]\
suspend fun [signUp](sign-up.md)(matricola: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), nome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), cognome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dataNascita: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html), sesso: [SessoUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.model/-sesso-utente/index.md), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): FirebaseUser?

Registra un nuovo utente con i dettagli forniti.

#### Return

Un FirebaseUser se la registrazione ha successo, null altrimenti.

#### Parameters

androidJvm

| | |
|---|---|
| matricola | La matricola dell'utente. |
| nome | Il nome dell'utente. |
| cognome | Il cognome dell'utente. |
| dataNascita | La data di nascita dell'utente. |
| sesso | Il sesso dell'utente. |
| email | L'indirizzo email dell'utente. |
| password | La password dell'utente. |

#### Throws

| | |
|---|---|
| [EmailAlreadyInUseException](../-email-already-in-use-exception/index.md) | se l'email è già in uso. |
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | per tutti gli altri errori che possono verificarsi. |
