//[app](../../../index.md)/[com.example.teamsync.util](../index.md)/[ThemePreferences](index.md)

# ThemePreferences

[androidJvm]\
object [ThemePreferences](index.md)

Oggetto per gestire le preferenze del tema dell'applicazione. Questo oggetto consente di salvare e recuperare la preferenza dell'utente riguardo il tema (scuro o chiaro) utilizzando [SharedPreferences](https://developer.android.com/reference/kotlin/android/content/SharedPreferences.html).

## Functions

| Name | Summary |
|---|---|
| [getTheme](get-theme.md) | [androidJvm]<br>fun [getTheme](get-theme.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Recupera la preferenza dell'utente per il tema. |
| [saveTheme](save-theme.md) | [androidJvm]<br>fun [saveTheme](save-theme.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), isDarkTheme: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Salva la preferenza dell'utente per il tema (scuro o chiaro). |
