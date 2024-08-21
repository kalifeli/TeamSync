//[app](../../index.md)/[com.example.teamsync.caratteristiche.leMieAttivita.ui](index.md)/[CondividiProgettoDialog](-condividi-progetto-dialog.md)

# CondividiProgettoDialog

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [CondividiProgettoDialog](-condividi-progetto-dialog.md)(onDismissRequest: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), viewModelProgetto: [ViewModelProgetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel/-view-model-progetto/index.md), contesto: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), progettoId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Composable per il dialog di condivisione del progetto.

Questo Composable visualizza un dialog che consente all'utente di condividere il codice di un progetto. Il codice viene recuperato dal ViewModel e visualizzato in un campo di testo non modificabile.

#### Parameters

androidJvm

| | |
|---|---|
| onDismissRequest | Funzione chiamata quando il dialog viene chiuso. |
| viewModelProgetto | ViewModel utilizzato per gestire i dati del progetto e recuperare il codice del progetto. |
| contesto | Contesto dell'applicazione, utilizzato per condividere il codice del progetto tramite Intent. |
| progettoId | L'ID del progetto per il quale si desidera condividere il codice. |
