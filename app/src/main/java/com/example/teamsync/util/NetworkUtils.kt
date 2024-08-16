package com.example.teamsync.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Controlla se una connessione internet è disponibile.
 *
 * Questa funzione verifica se l'applicazione è attualmente connessa a internet
 * attraverso Wi-Fi, rete cellulare o Ethernet. Viene utilizzato il [ConnectivityManager]
 * per ottenere lo stato attuale della rete e le sue capacità.
 *
 * @param contesto Il contesto dell'applicazione o dell'attività da cui viene chiamata la funzione.
 * @return `true` se è disponibile una connessione internet, `false` altrimenti.
 */
fun isInternetAvailable(contesto: Context): Boolean{
    val connectivityManager = contesto.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkAttivo = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(networkAttivo) ?: return false
    return when{
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}