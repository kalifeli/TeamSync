package com.example.teamsync

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.teamsync.navigation.NavGraph
import com.example.teamsync.util.theme.TeamSyncTheme

/**
 * La classe principale dell'applicazione, che estende [ComponentActivity].
 * Questa classe rappresenta l'entry point dell'app e gestisce il ciclo di vita principale
 * dell'attività. Durante l'onCreate, la schermata di splash viene installata e viene avviata
 * la composizione dell'interfaccia utente utilizzando Jetpack Compose.
 */
class MainActivity : ComponentActivity() {

    /**
     * Metodo chiamato quando l'attività viene creata. Questo è il punto in cui viene inizializzata
     * l'interfaccia utente dell'app. Viene utilizzato Jetpack Compose per impostare il contenuto
     * della schermata. La funzione installSplashScreen() viene chiamata per mostrare una
     * schermata di splash fino a quando l'app non è pronta.
     *
     * @param savedInstanceState Se l'attività viene ricreata, questo parametro contiene i dati
     * dell'istanza precedente.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TeamSyncTheme {
                NavGraph()
            }
        }
    }
}



