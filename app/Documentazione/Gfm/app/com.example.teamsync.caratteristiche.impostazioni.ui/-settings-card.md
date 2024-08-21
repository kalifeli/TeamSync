//[app](../../index.md)/[com.example.teamsync.caratteristiche.impostazioni.ui](index.md)/[SettingsCard](-settings-card.md)

# SettingsCard

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [SettingsCard](-settings-card.md)(settingsList: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SettingItem](-setting-item/index.md)&gt;, navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), viewModelUtente: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), isDarkTheme: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), isDeleteAccount: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)

Composable per visualizzare una scheda contenente una lista di elementi di impostazione.

#### Parameters

androidJvm

| | |
|---|---|
| settingsList | Lista di elementi di impostazione da visualizzare. |
| navController | Il controller di navigazione per gestire la navigazione tra le schermate. |
| viewModelUtente | Il ViewModel che gestisce lo stato e la logica dell'utente. |
| isDarkTheme | Flag che indica se il tema scuro è abilitato. |
| isDeleteAccount | Flag che indica se la scheda contiene l'opzione per eliminare l'account. |
