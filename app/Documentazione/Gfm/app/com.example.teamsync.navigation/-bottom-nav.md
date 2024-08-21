//[app](../../index.md)/[com.example.teamsync.navigation](index.md)/[BottomNav](-bottom-nav.md)

# BottomNav

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [BottomNav](-bottom-nav.md)(navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), notificheViewModel: [ViewModelNotifiche](../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md))

Composable per la barra di navigazione inferiore (Bottom Navigation Bar) dell'app.

La funzione BottomNav crea una barra di navigazione inferiore con tre elementi: Home, Notifiche e Profilo. Ogni elemento della barra di navigazione è associato a una rotta specifica nel `NavHostController` e permette di navigare tra le diverse schermate dell'app.

#### Parameters

androidJvm

| | |
|---|---|
| navController | Il controller di navigazione per gestire le navigazioni tra schermate. |
| notificheViewModel | Il ViewModel utilizzato per recuperare le notifiche e gestirne lo stato. |
