//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)/[getProgettoIdByCodice](get-progetto-id-by-codice.md)

# getProgettoIdByCodice

[androidJvm]\
suspend fun [getProgettoIdByCodice](get-progetto-id-by-codice.md)(codice: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?

Funzione che recupera l'ID di un progetto in base a un codice specificato.

#### Return

L'ID del progetto corrispondente al codice specificato, oppure `null` se non viene trovato alcun progetto o in caso di errore.

La funzione utilizza Firestore per interrogare la collezione &quot;progetti&quot; e cercare un documento in cui il campo &quot;codice&quot; corrisponde al valore specificato. Se l'operazione ha successo e viene trovato un progetto, l'ID del progetto viene restituito. In caso di eccezione o se non viene trovato alcun progetto, viene restituito `null`. La funzione è una funzione sospesa e deve essere chiamata all'interno di una coroutine o di un'altra funzione sospesa.

#### Parameters

androidJvm

| | |
|---|---|
| codice | Il codice del progetto di cui si vuole ottenere l'ID. |
