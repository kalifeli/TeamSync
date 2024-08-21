//[app](../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.ui](index.md)/[ListaColleghi](-lista-colleghi.md)

# ListaColleghi

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [ListaColleghi](-lista-colleghi.md)(viewModel: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), leMieAttivitaViewModel: [LeMieAttivitaViewModel](../com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel/-le-mie-attivita-view-model/index.md), idTask: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), viewModelProgetto: [ViewModelProgetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel/-view-model-progetto/index.md), idProgetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), viewModelNotifiche: [ViewModelNotifiche](../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md))

Composable che visualizza l'elenco dei colleghi a cui può essere delegata un'attività.

#### Parameters

androidJvm

| | |
|---|---|
| viewModel | Il ViewModel utilizzato per gestire le informazioni del profilo utente. |
| navController | Il NavHostController utilizzato per navigare tra le schermate. |
| leMieAttivitaViewModel | Il ViewModel utilizzato per gestire le attività. |
| idTask | L'ID dell'attività da delegare. |
| viewModelProgetto | Il ViewModel utilizzato per gestire i progetti e ottenere la lista dei partecipanti. |
| idProgetto | L'ID del progetto associato all'attività. |
| viewModelNotifiche | Il ViewModel utilizzato per gestire le notifiche. |
