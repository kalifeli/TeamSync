//[app](../../index.md)/[com.example.teamsync.caratteristiche.profilo](index.md)/[ProfiloUtenteCliccato](-profilo-utente-cliccato.md)

# ProfiloUtenteCliccato

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [ProfiloUtenteCliccato](-profilo-utente-cliccato.md)(viewModelUtente: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), leMieAttivitaViewModel: [LeMieAttivitaViewModel](../com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel/-le-mie-attivita-view-model/index.md), navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), amicizia: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), provenienza: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), task: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), progetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), viewModelprogetto: [ViewModelProgetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel/-view-model-progetto/index.md), viewModelNotifiche: [ViewModelNotifiche](../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md), sottoprovenienza: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Funzione Composable per visualizzare il profilo di un utente selezionato.

#### Parameters

androidJvm

| | |
|---|---|
| viewModelUtente | Il ViewModel per gestire le operazioni del profilo utente |
| leMieAttivitaViewModel | Il ViewModel per gestire le operazioni sulle attivita |
| navController | Il NavController per navigare tra le schermate. |
| id | L'ID dell'utente selezionato. |
| amicizia | Stato dell'amicizia tra l'utente attuale e l'utente selezionato. |
| provenienza | La provenienza della navigazione. |
| task | L'ID del task associato (se presente). |
| progetto | L'ID del progetto associato (se presente). |
| viewModelprogetto | Il ViewModel per gestire le operazioni dei progetti. |
| viewModelNotifiche | Il ViewModel per gestire le notifiche. |
| sottoprovenienza | La sottoprovenienza della navigazione. |
