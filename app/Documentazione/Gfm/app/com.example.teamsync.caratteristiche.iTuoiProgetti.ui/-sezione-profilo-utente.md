//[app](../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.ui](index.md)/[SezioneProfiloUtente](-sezione-profilo-utente.md)

# SezioneProfiloUtente

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [SezioneProfiloUtente](-sezione-profilo-utente.md)(navController: [NavController](https://developer.android.com/reference/kotlin/androidx/navigation/NavController.html), viewModelUtente: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), isDarkTheme: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))

Composable che mostra una sezione del profilo utente con nome e immagine. Include una scheda elevata (ElevatedCard) che, se cliccata, naviga alla schermata del profilo utente.

#### Parameters

androidJvm

| | |
|---|---|
| navController | Controller di navigazione per gestire la navigazione all'interno dell'app. |
| viewModelUtente | ViewModel che gestisce i dati del profilo utente. |
| isDarkTheme | Indica se l'app è in modalità tema scuro. |
