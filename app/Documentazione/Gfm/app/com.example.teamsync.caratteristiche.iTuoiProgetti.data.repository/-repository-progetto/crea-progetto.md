//[app](../../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.data.repository](../index.md)/[RepositoryProgetto](index.md)/[creaProgetto](crea-progetto.md)

# creaProgetto

[androidJvm]\
suspend fun [creaProgetto](crea-progetto.md)(progetto: [Progetto](../../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Funzione che crea un nuovo progetto nel database Firestore.

#### Return

L'ID del documento creato in Firestore.

La funzione utilizza Firestore per aggiungere un nuovo documento alla collezione &quot;progetti&quot;. Se l'operazione ha successo, l'ID del documento creato viene restituito. La funzione è una funzione sospesa e deve essere chiamata all'interno di una coroutine o di un'altra funzione sospesa.

#### Parameters

androidJvm

| | |
|---|---|
| progetto | Un'istanza della classe `Progetto` che contiene i dettagli del progetto da creare. |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | Se si verifica un errore durante l'aggiunta del documento su Firestore, l'eccezione sarà rilanciata. |
