//[app](../../../index.md)/[com.example.teamsync.caratteristiche.autentificazione.data.repository](../index.md)/[RepositoryUtente](index.md)

# RepositoryUtente

[androidJvm]\
class [RepositoryUtente](index.md)

Repository per gestire le operazioni relative alle attività.

## Constructors

| | |
|---|---|
| [RepositoryUtente](-repository-utente.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [aggiungiAmico](aggiungi-amico.md) | [androidJvm]<br>suspend fun [aggiungiAmico](aggiungi-amico.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amicoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Aggiunge un amico all'utente con l'ID fornito. |
| [eliminaAccount](elimina-account.md) | [androidJvm]<br>suspend fun [eliminaAccount](elimina-account.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Elimina l'account di un utente dato il suo ID. |
| [getAllUtenti](get-all-utenti.md) | [androidJvm]<br>suspend fun [getAllUtenti](get-all-utenti.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Ottiene tutti gli ID degli utenti. |
| [getUserProfile](get-user-profile.md) | [androidJvm]<br>suspend fun [getUserProfile](get-user-profile.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [ProfiloUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md)?<br>Ottiene il profilo dell'utente con l'ID fornito. |
| [getUserSincrono](get-user-sincrono.md) | [androidJvm]<br>fun [getUserSincrono](get-user-sincrono.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), callback: ([ProfiloUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md)?) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))<br>Ottiene il profilo utente in modo sincrono. |
| [getUtenteAttuale](get-utente-attuale.md) | [androidJvm]<br>fun [getUtenteAttuale](get-utente-attuale.md)(): FirebaseUser?<br>Ottiene l'utente attualmente loggato. |
| [getUtenteAttualeID](get-utente-attuale-i-d.md) | [androidJvm]<br>fun [getUtenteAttualeID](get-utente-attuale-i-d.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [isEmailVerified](is-email-verified.md) | [androidJvm]<br>suspend fun [isEmailVerified](is-email-verified.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Verifica se l'email dell'utente attualmente loggato è verificata. |
| [isFirstLogin](is-first-login.md) | [androidJvm]<br>suspend fun [isFirstLogin](is-first-login.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Verifica se l'utente sta effettuando il primo accesso. |
| [login](login.md) | [androidJvm]<br>suspend fun [login](login.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): FirebaseUser?<br>Effettua il login di un utente con l'email e la password fornite. |
| [resetPassword](reset-password.md) | [androidJvm]<br>suspend fun [resetPassword](reset-password.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Invia una email di reset della password all'utente con l'indirizzo email fornito. |
| [rimuoviAmiciNonEsistenti](rimuovi-amici-non-esistenti.md) | [androidJvm]<br>suspend fun [rimuoviAmiciNonEsistenti](rimuovi-amici-non-esistenti.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amici: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Verifica l'esistenza dei profili associati alla lista di amici e rimuove quelli che non esistono più nel database. |
| [rimuoviAmico](rimuovi-amico.md) | [androidJvm]<br>suspend fun [rimuoviAmico](rimuovi-amico.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amicoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Rimuove un amico dall'utente con l'ID fornito. |
| [sendEmailVerification](send-email-verification.md) | [androidJvm]<br>fun [sendEmailVerification](send-email-verification.md)()<br>Invia un'email di verifica all'utente attualmente loggato. |
| [signOut](sign-out.md) | [androidJvm]<br>fun [signOut](sign-out.md)()<br>Effettua il logout dell'utente attualmente loggato. |
| [signUp](sign-up.md) | [androidJvm]<br>suspend fun [signUp](sign-up.md)(matricola: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), nome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), cognome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), dataNascita: [Date](https://developer.android.com/reference/kotlin/java/util/Date.html), sesso: [SessoUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.model/-sesso-utente/index.md), email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): FirebaseUser?<br>Registra un nuovo utente con i dettagli forniti. |
| [updateFirstLogin](update-first-login.md) | [androidJvm]<br>suspend fun [updateFirstLogin](update-first-login.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Aggiorna lo stato del primo accesso per l'utente con l'ID fornito. |
| [updateUserProfile](update-user-profile.md) | [androidJvm]<br>suspend fun [updateUserProfile](update-user-profile.md)(profiloUtente: [ProfiloUtente](../../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md))<br>Aggiorna il profilo dell'utente con i nuovi dettagli forniti. |
