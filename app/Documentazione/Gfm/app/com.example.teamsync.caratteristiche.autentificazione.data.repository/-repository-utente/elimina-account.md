//[app](../../../index.md)/[com.example.teamsync.caratteristiche.autentificazione.data.repository](../index.md)/[RepositoryUtente](index.md)/[eliminaAccount](elimina-account.md)

# eliminaAccount

[androidJvm]\
suspend fun [eliminaAccount](elimina-account.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?

Elimina l'account di un utente dato il suo ID.

Questa funzione esegue due passaggi per eliminare completamente un account utente:

1. 
   Elimina i dati dell'utente dal database Firestore.
2. 
   Se l'utente attualmente autenticato ha lo stesso ID fornito, elimina anche l'account     dall'autenticazione di Firebase.

Se l'eliminazione dell'account ha successo, la funzione restituisce `null`. In caso di errori durante l'eliminazione dei dati o dell'account, restituisce un messaggio di errore.

#### Return

Una stringa contenente un messaggio di errore in caso di fallimento, oppure `null` se l'eliminazione ha successo.

#### Parameters

androidJvm

| | |
|---|---|
| userId | L'ID dell'utente di cui si vuole eliminare l'account. |
