//[app](../../index.md)/[com.example.teamsync.caratteristiche.iTuoiProgetti.ui](index.md)/[ListaColleghi](-lista-colleghi.md)

# ListaColleghi

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [ListaColleghi](-lista-colleghi.md)(viewModelUtente: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), viewModelProgetto: [ViewModelProgetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel/-view-model-progetto/index.md), idProgetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), partecipanti: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, nomeProgetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), viewNotifiche: [ViewModelNotifiche](../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md), sottoprovenienza: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), provenienza: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Funzione composable per visualizzare la lista dei colleghi partecipanti a un progetto.

#### Parameters

androidJvm

| | |
|---|---|
| viewModelUtente | ViewModel per gestire le operazioni dell'utente. |
| navController | Controller per la navigazione tra le schermate. |
| viewModelProgetto | ViewModel per gestire le operazioni del progetto. |
| idProgetto | ID del progetto. |
| partecipanti | Lista degli ID dei partecipanti. |
| nomeProgetto | Nome del progetto. |
| viewNotifiche | ViewModel per gestire le notifiche. |
| sottoprovenienza | Sottoprovenienza della navigazione. |
| provenienza | Provenienza della navigazione. |
