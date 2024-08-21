//[app](../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.ui](index.md)/[CompleteDialog](-complete-dialog.md)

# CompleteDialog

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [CompleteDialog](-complete-dialog.md)(sezione: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), onDismiss: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), onSave: ([LeMieAttivita](../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), item: [LeMieAttivita](../com.example.teamsync.caratteristiche.leMieAttivita.data.model/-le-mie-attivita/index.md))

Composable per il dialogo di completamento di un'attività.

#### Parameters

androidJvm

| | |
|---|---|
| sezione | La sezione a cui appartiene l'attività. |
| onDismiss | Funzione da chiamare quando il dialogo viene chiuso. |
| onSave | Funzione da chiamare quando l'attività viene completata. |
| item | L'attività da completare. |
