//[app](../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.ui](index.md)/[CollegaItem](-collega-item.md)

# CollegaItem

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [CollegaItem](-collega-item.md)(utente: [ProfiloUtente](../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md), color: [Color](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Color.html), navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), userLoggato: [ProfiloUtente](../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md)?, partecipa: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), leMieAttivitaViewModel: [LeMieAttivitaViewModel](../com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel/-le-mie-attivita-view-model/index.md), idTask: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), idProg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), viewModelNotifiche: [ViewModelNotifiche](../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md))

Composable che rappresenta un singolo collega nell'elenco dei partecipanti.

#### Parameters

androidJvm

| | |
|---|---|
| utente | Il profilo del collega. |
| color | Il colore da utilizzare per lo sfondo e altri elementi visivi. |
| navController | Il NavHostController utilizzato per navigare tra le schermate. |
| userLoggato | Il profilo dell'utente loggato. |
| partecipa | Indica se il collega partecipa già all'attività. |
| leMieAttivitaViewModel | Il ViewModel utilizzato per gestire le attività. |
| idTask | L'ID dell'attività da delegare. |
| idProg | L'ID del progetto associato all'attività. |
| viewModelNotifiche | Il ViewModel utilizzato per gestire le notifiche. |
