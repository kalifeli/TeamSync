//[app](../../index.md)/[com.example.teamsync.util](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ImageUploader](-image-uploader/index.md) | [androidJvm]<br>class [ImageUploader](-image-uploader/index.md) |
| [Priorita](-priorita/index.md) | [androidJvm]<br>enum [Priorita](-priorita/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Priorita](-priorita/index.md)&gt; <br>Enum class che rappresenta i diversi livelli di priorità per le attività o i task. Ogni livello di priorità è associato a un colore specifico che può essere utilizzato per visualizzare graficamente l'importanza di un task. |
| [ThemePreferences](-theme-preferences/index.md) | [androidJvm]<br>object [ThemePreferences](-theme-preferences/index.md)<br>Oggetto per gestire le preferenze del tema dell'applicazione. Questo oggetto consente di salvare e recuperare la preferenza dell'utente riguardo il tema (scuro o chiaro) utilizzando [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html). |

## Functions

| Name | Summary |
|---|---|
| [isInternetAvailable](is-internet-available.md) | [androidJvm]<br>fun [isInternetAvailable](is-internet-available.md)(contesto: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Controlla se una connessione internet è disponibile. |
| [NoInternetScreen](-no-internet-screen.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [NoInternetScreen](-no-internet-screen.md)(isDarkTheme: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), onRetry: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))<br>Visualizza una schermata che informa l'utente della mancanza di connessione Internet. |
| [NoInternetScreenDarkPreview](-no-internet-screen-dark-preview.md) | [androidJvm]<br>@[Preview](https://developer.android.com/reference/kotlin/androidx/compose/ui/tooling/preview/Preview.html)(showBackground = true)<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [NoInternetScreenDarkPreview](-no-internet-screen-dark-preview.md)() |
| [NoInternetScreenPreview](-no-internet-screen-preview.md) | [androidJvm]<br>@[Preview](https://developer.android.com/reference/kotlin/androidx/compose/ui/tooling/preview/Preview.html)(showBackground = true)<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [NoInternetScreenPreview](-no-internet-screen-preview.md)() |
