//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)

# RepositoryProgetto

[androidJvm]\
class [RepositoryProgetto](index.md)

## Constructors

| | |
|---|---|
| [RepositoryProgetto](-repository-progetto.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [abbandonaProgetto](abbandona-progetto.md) | [androidJvm]<br>suspend fun [abbandonaProgetto](abbandona-progetto.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), callback: ([Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))<br>Funzione che permette a un utente di abbandonare un progetto. |
| [aggiornaProgetto](aggiorna-progetto.md) | [androidJvm]<br>suspend fun [aggiornaProgetto](aggiorna-progetto.md)(progetto: [Progetto](../../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md))<br>Aggiorna i dati di un progetto nel database. |
| [aggiungiPartecipante](aggiungi-partecipante.md) | [androidJvm]<br>suspend fun [aggiungiPartecipante](aggiungi-partecipante.md)(progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Funzione che permette di aggiungere un partecipante a un progetto. |
| [creaProgetto](crea-progetto.md) | [androidJvm]<br>suspend fun [creaProgetto](crea-progetto.md)(progetto: [Progetto](../../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Funzione che crea un nuovo progetto nel database Firestore. |
| [eliminaNotificheDelProgetto](elimina-notifiche-del-progetto.md) | [androidJvm]<br>suspend fun [eliminaNotificheDelProgetto](elimina-notifiche-del-progetto.md)(progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Elimina le notifiche associate a un progetto. |
| [generaCodiceProgetto](genera-codice-progetto.md) | [androidJvm]<br>fun [generaCodiceProgetto](genera-codice-progetto.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Funzione che genera un codice univoco per un progetto. |
| [getPartecipantiDelProgetto](get-partecipanti-del-progetto.md) | [androidJvm]<br>suspend fun [getPartecipantiDelProgetto](get-partecipanti-del-progetto.md)(progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Recupera la lista dei partecipanti di un progetto. |
| [getProgettiCompletatiUtente](get-progetti-completati-utente.md) | [androidJvm]<br>suspend fun [getProgettiCompletatiUtente](get-progetti-completati-utente.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Progetto](../../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md)&gt;<br>Recupera la lista dei progetti completati a cui partecipa un utente specifico. |
| [getProgettiUtente](get-progetti-utente.md) | [androidJvm]<br>suspend fun [getProgettiUtente](get-progetti-utente.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Progetto](../../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md)&gt;<br>Funzione che recupera la lista dei progetti a cui partecipa un utente specifico. |
| [getProgettoById](get-progetto-by-id.md) | [androidJvm]<br>suspend fun [getProgettoById](get-progetto-by-id.md)(progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Progetto](../../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md)?<br>Recupera un progetto dato il suo ID. |
| [getProgettoIdByCodice](get-progetto-id-by-codice.md) | [androidJvm]<br>suspend fun [getProgettoIdByCodice](get-progetto-id-by-codice.md)(codice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?<br>Funzione che recupera l'ID di un progetto in base a un codice specificato. |
| [getUtenteCorrente](get-utente-corrente.md) | [androidJvm]<br>fun [getUtenteCorrente](get-utente-corrente.md)(): FirebaseUser?<br>Funzione che recupera l'utente attualmente autenticato. |
| [progettiutenteCallback](progettiutente-callback.md) | [androidJvm]<br>suspend fun [progettiutenteCallback](progettiutente-callback.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), callback: ([List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Progetto](../../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md)&gt;) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))<br>Recupera la lista dei progetti a cui partecipa un utente specifico utilizzando una callback. |
