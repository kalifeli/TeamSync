//[app](../../index.md)/[com.example.teamsync.util](index.md)/[NoInternetScreen](-no-internet-screen.md)

# NoInternetScreen

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [NoInternetScreen](-no-internet-screen.md)(isDarkTheme: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), onRetry: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))

Visualizza una schermata che informa l'utente della mancanza di connessione Internet.

Questa funzione composable viene utilizzata per visualizzare una schermata di avviso quando non è disponibile una connessione Internet. Mostra un'immagine, un messaggio di testo e un pulsante per riprovare a stabilire la connessione.

#### Parameters

androidJvm

| | |
|---|---|
| isDarkTheme | Booleano che indica se il tema corrente è scuro. Utilizzato per scegliere i colori appropriati. |
| onRetry | Callback che viene eseguito quando l'utente preme il pulsante &quot;Riprova&quot;.     Questo permette di tentare nuovamente la connessione. |
