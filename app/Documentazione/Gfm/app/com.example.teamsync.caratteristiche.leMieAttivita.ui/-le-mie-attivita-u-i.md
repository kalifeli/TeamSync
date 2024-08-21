//[app](../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.ui](index.md)/[LeMieAttivitaUI](-le-mie-attivita-u-i.md)

# LeMieAttivitaUI

[androidJvm]\

@[RequiresApi](https://developer.android.com/reference/kotlin/androidx/annotation/RequiresApi.html)(value = 33)

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [LeMieAttivitaUI](-le-mie-attivita-u-i.md)(navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), viewModel: [LeMieAttivitaViewModel](../com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel/-le-mie-attivita-view-model/index.md), viewModelUtente: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), viewmodelprogetto: [ViewModelProgetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel/-view-model-progetto/index.md), idProg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), viewModelNotifiche: [ViewModelNotifiche](../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md))

Composable principale per la gestione dell'interfaccia utente delle attività.

#### Parameters

androidJvm

| | |
|---|---|
| navController | Controller di navigazione per spostarsi tra le schermate. |
| viewModel | ViewModel per la gestione delle attività. |
| viewModelUtente | ViewModel per la gestione del profilo utente. |
| viewmodelprogetto | ViewModel per la gestione del progetto. |
| idProg | ID del progetto corrente. |
| viewModelNotifiche | ViewModel per la gestione delle notifiche. |
