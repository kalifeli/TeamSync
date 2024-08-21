//[app](../../index.md)/[com.example.teamsync.caratteristiche.impostazioni.ui](index.md)/[SettingItemRow](-setting-item-row.md)

# SettingItemRow

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [SettingItemRow](-setting-item-row.md)(setting: [SettingItem](-setting-item/index.md), navController: [NavHostController](https://developer.android.com/reference/kotlin/androidx/navigation/NavHostController.html), viewModelUtente: [ViewModelUtente](../com.example.teamsync.caratteristiche.autentificazione.data.viewModel/-view-model-utente/index.md), isDarkTheme: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), isDeleteAccount: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))

Composable per visualizzare una riga contenente un singolo elemento di impostazione.

#### Parameters

androidJvm

| | |
|---|---|
| setting | L'elemento di impostazione da visualizzare. |
| navController | Il controller di navigazione per gestire la navigazione tra le schermate. |
| viewModelUtente | Il ViewModel che gestisce lo stato e la logica dell'utente. |
| isDarkTheme | Flag che indica se il tema scuro è abilitato. |
| isDeleteAccount | Flag che indica se l'elemento di impostazione riguarda l'eliminazione dell'account. |
