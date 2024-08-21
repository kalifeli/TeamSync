//[app](../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.ui](index.md)/[DelegaTask](-delega-task.md)

# DelegaTask

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [DelegaTask](-delega-task.md)(viewModel: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), leMieAttivitaViewModel: [LeMieAttivitaViewModel](../com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel/-le-mie-attivita-view-model/index.md), idTask: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), viewModelProgetto: [ViewModelProgetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel/-view-model-progetto/index.md), idProg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), viewModelNotifiche: [ViewModelNotifiche](../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md))

Composable che rappresenta la schermata per delegare un'attività a un collega.

#### Parameters

androidJvm

| | |
|---|---|
| viewModel | Il ViewModel utilizzato per gestire le informazioni del profilo utente. |
| navController | Il NavHostController utilizzato per navigare tra le schermate. |
| leMieAttivitaViewModel | Il ViewModel utilizzato per gestire le attività. |
| idTask | L'ID dell'attività da delegare. |
| viewModelProgetto | Il ViewModel utilizzato per gestire i progetti e ottenere la lista dei partecipanti. |
| idProg | L'ID del progetto associato all'attività. |
| viewModelNotifiche | Il ViewModel utilizzato per gestire le notifiche. |
