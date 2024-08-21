//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)/[getProgettiUtente](get-progetti-utente.md)

# getProgettiUtente

[androidJvm]\
suspend fun [getProgettiUtente](get-progetti-utente.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Progetto](../../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md)&gt;

Funzione che recupera la lista dei progetti a cui partecipa un utente specifico.

#### Return

Una lista di oggetti `Progetto` a cui l'utente partecipa. Restituisce una lista vuota in caso di errore.

La funzione utilizza Firestore per interrogare la collezione &quot;progetti&quot; e recuperare tutti i documenti in cui il campo &quot;partecipanti&quot; contiene l'ID dell'utente specificato. Se l'operazione ha successo, la lista di progetti viene restituita. In caso di eccezione, viene restituita una lista vuota. La funzione è una funzione sospesa e deve essere chiamata all'interno di una coroutine o di un'altra funzione sospesa.

#### Parameters

androidJvm

| | |
|---|---|
| userId | L'ID dell'utente di cui si vogliono recuperare i progetti. |
