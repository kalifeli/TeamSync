//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)/[getProgettiCompletatiUtente](get-progetti-completati-utente.md)

# getProgettiCompletatiUtente

[androidJvm]\
suspend fun [getProgettiCompletatiUtente](get-progetti-completati-utente.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Progetto](../../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md)&gt;

Recupera la lista dei progetti completati a cui partecipa un utente specifico.

#### Return

Una lista di oggetti `Progetto` completati a cui l'utente partecipa. Restituisce una lista vuota in caso di errore.

#### Parameters

androidJvm

| | |
|---|---|
| userId | L'ID dell'utente di cui si vogliono recuperare i progetti completati. |
