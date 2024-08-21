//[app](../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.ui](index.md)/[TodoItem](-todo-item.md)

# TodoItem

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [TodoItem](-todo-item.md)(item: [LeMieAttivita](../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md), onDelete: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onEdit: ([LeMieAttivita](../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onComplete: ([LeMieAttivita](../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), viewModelUtente: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), userProfile: [ProfiloUtente](../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md)?)

Composable per rappresentare un singolo elemento Attività.

#### Parameters

androidJvm

| | |
|---|---|
| item | L'attività da visualizzare. |
| onDelete | Funzione da chiamare quando l'attività viene eliminata. |
| onEdit | Funzione da chiamare quando l'attività viene modificata. |
| onComplete | Funzione da chiamare quando l'attività viene completata. |
| viewModelUtente | ViewModel per la gestione del profilo utente. |
| userProfile | Profilo dell'utente corrente. |
