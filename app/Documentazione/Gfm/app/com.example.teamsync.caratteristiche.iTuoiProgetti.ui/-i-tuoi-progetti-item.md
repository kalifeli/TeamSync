//[app](../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.ui](index.md)/[ITuoiProgettiItem](-i-tuoi-progetti-item.md)

# ITuoiProgettiItem

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [ITuoiProgettiItem](-i-tuoi-progetti-item.md)(progetto: [Progetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md), navController: [NavController](https://developer.android.com/reference/kotlin/androidx/navigation/NavController.html), viewModelProgetto: [ViewModelProgetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel/-view-model-progetto/index.md), attivitaNonCompletate: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), isDarkTheme: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))

Composable che visualizza un singolo elemento progetto.

#### Parameters

androidJvm

| | |
|---|---|
| progetto | L'oggetto Progetto da visualizzare. |
| navController | Il controller di navigazione per gestire la navigazione tra schermate. |
| viewModelProgetto | Il ViewModel associato ai progetti, per gestire le operazioni legate ai progetti. |
| attivitaNonCompletate | Il numero di attività non completate associate al progetto. |
| isDarkTheme | Booleano che indica se l'app è in modalità tema scuro. |
