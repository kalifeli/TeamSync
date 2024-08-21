//[app](../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.ui](index.md)/[SezioneITUoiProgetti](-sezione-i-t-uoi-progetti.md)

# SezioneITUoiProgetti

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [SezioneITUoiProgetti](-sezione-i-t-uoi-progetti.md)(progetti: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Progetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md)&gt;, navController: [NavController](https://developer.android.com/reference/kotlin/androidx/navigation/NavController.html), attivitaProgetti: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;, viewModelProgetto: [ViewModelProgetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel/-view-model-progetto/index.md), isDarkTheme: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))

Composable che visualizza la sezione &quot;I Tuoi Progetti&quot; con una lista di progetti. I progetti possono essere ordinati per data di creazione, scadenza o priorità, e possono essere filtrati per mostrare o nascondere i progetti completati.

#### Parameters

androidJvm

| | |
|---|---|
| progetti | Lista di progetti dell'utente. |
| navController | Controller di navigazione per navigare tra le schermate. |
| attivitaProgetti | Mappa delle attività non completate per ciascun progetto. |
| viewModelProgetto | ViewModel per gestire i dati e le operazioni sui progetti. |
| isDarkTheme | Indica se l'app è in modalità tema scuro. |
