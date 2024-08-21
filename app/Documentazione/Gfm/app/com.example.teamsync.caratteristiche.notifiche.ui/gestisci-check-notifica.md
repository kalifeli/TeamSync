//[app](../../index.md)/[com.example.teamsync.caratteristiche.notifiche.ui](index.md)/[gestisciCheckNotifica](gestisci-check-notifica.md)

# gestisciCheckNotifica

[androidJvm]\
fun [gestisciCheckNotifica](gestisci-check-notifica.md)(navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), notifica: [Notifiche](../com.example.teamsync.caratteristiche.notifiche.data.model/-notifiche/index.md), userProfile: [ProfiloUtente](../com.example.teamsync.caratteristiche.autentificazione.data.model/-profilo-utente/index.md), vmNotifiche: [ViewModelNotifiche](../com.example.teamsync.caratteristiche.notifiche.data.viewModel/-view-model-notifiche/index.md), viewmodelProgetto: [ViewModelProgetto](../com.example.teamsync.caratteristiche.iTuoiProgetti.data.viewModel/-view-model-progetto/index.md), viewModelUtente: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), listap: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;?, nomeProgetto: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Gestisce il click sull'icona di check nelle notifiche per accettare una richiesta di progetto o amicizia.

#### Parameters

androidJvm

| | |
|---|---|
| navController | Il controller per la navigazione tra le schermate. |
| notifica | La notifica selezionata. |
| userProfile | Il profilo utente corrente. |
| vmNotifiche | Il ViewModel delle notifiche. |
| viewmodelProgetto | Il ViewModel del progetto. |
| viewModelUtente | Il ViewModel dell'utente. |
| listap | La lista dei partecipanti al progetto. |
| nomeProgetto | Il nome del progetto associato alla notifica. |
