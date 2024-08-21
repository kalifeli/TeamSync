//[app](../../index.md)/[com.example.teamsync.util](index.md)/[isInternetAvailable](is-internet-available.md)

# isInternetAvailable

[androidJvm]\
fun [isInternetAvailable](is-internet-available.md)(contesto: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Controlla se una connessione internet è disponibile.

Questa funzione verifica se l'applicazione è attualmente connessa a internet attraverso Wi-Fi, rete cellulare o Ethernet. Viene utilizzato il [ConnectivityManager](https://developer.android.com/reference/kotlin/android/net/ConnectivityManager.html) per ottenere lo stato attuale della rete e le sue capacità.

#### Return

`true` se è disponibile una connessione internet, `false` altrimenti.

#### Parameters

androidJvm

| | |
|---|---|
| contesto | Il contesto dell'applicazione o dell'attività da cui viene chiamata la funzione. |
