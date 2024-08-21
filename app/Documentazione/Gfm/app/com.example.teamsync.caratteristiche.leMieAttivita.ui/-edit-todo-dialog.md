//[app](../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.ui](index.md)/[EditTodoDialog](-edit-todo-dialog.md)

# EditTodoDialog

[androidJvm]\

@[RequiresApi](https://developer.android.com/reference/kotlin/androidx/annotation/RequiresApi.html)(value = 33)

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [EditTodoDialog](-edit-todo-dialog.md)(todoItem: [LeMieAttivita](../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md), onDismiss: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onSave: ([LeMieAttivita](../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), viewModel: [LeMieAttivitaViewModel](../com.example.teamsync.caratteristiche.leMieAttivita.data.viewModel/-le-mie-attivita-view-model/index.md), viewModelNotifiche: [ViewModelNotifiche](../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md), progettoNome: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), userProfile: [ProfiloUtente](../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md)?, progetto: [Progetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.model/-progetto/index.md))

Composable per la dialog di modifica di un'attività.

#### Parameters

androidJvm

| | |
|---|---|
| todoItem | L'attività da modificare. |
| onDismiss | Funzione da chiamare quando il dialogo viene chiuso. |
| onSave | Funzione da chiamare quando l'attività viene salvata. |
| navController | Controller di navigazione per spostarsi tra le schermate. |
| viewModel | ViewModel per la gestione delle attività. |
| viewModelNotifiche | ViewModel per la gestione delle notifiche. |
| progettoNome | Nome del progetto associato all'attività. |
| userProfile | Profilo dell'utente corrente. |
