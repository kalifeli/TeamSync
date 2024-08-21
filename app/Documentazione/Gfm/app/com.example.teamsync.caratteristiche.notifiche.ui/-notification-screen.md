//[app](../../index.md)/[com.example.teamsync.caratteristiche.notifiche.ui](index.md)/[NotificationScreen](-notification-screen.md)

# NotificationScreen

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [NotificationScreen](-notification-screen.md)(viewModelUtente: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), notificheModel: [ViewModelNotifiche](../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md))

Schermata delle notifiche per l'applicazione.

Questa funzione mostra le notifiche dell'utente corrente, consentendo di visualizzare, contrassegnare come lette o eliminare le notifiche. La schermata supporta il tema chiaro e scuro e gestisce automaticamente lo stato della connessione internet e il caricamento dei dati.

#### Parameters

androidJvm

| | |
|---|---|
| viewModelUtente | Il ViewModel associato al profilo utente. |
| navController | Il controller per la navigazione tra le schermate. |
| notificheModel | Il ViewModel che gestisce le notifiche. |
