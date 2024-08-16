package com.example.teamsync.util


import android.content.Context
import android.content.SharedPreferences

/**
 * Oggetto per gestire le preferenze del tema dell'applicazione.
 * Questo oggetto consente di salvare e recuperare la preferenza
 * dell'utente riguardo il tema (scuro o chiaro) utilizzando
 * [SharedPreferences].
 */
object ThemePreferences {
    private const val PREFERENCES_NAME = "theme_preferences"
    private const val THEME_KEY = "theme_key"

    /**
     * Recupera un'istanza di [SharedPreferences] utilizzata per memorizzare
     * le preferenze del tema.
     *
     * @param context Il contesto dell'applicazione o dell'attività.
     * @return L'istanza di [SharedPreferences] che gestisce le preferenze del tema.
     */
    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Salva la preferenza dell'utente per il tema (scuro o chiaro).
     *
     * @param context Il contesto dell'applicazione o dell'attività.
     * @param isDarkTheme Booleano che indica se il tema scuro è abilitato (true) o meno (false).
     */
    fun saveTheme(context: Context, isDarkTheme: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(THEME_KEY, isDarkTheme)
        editor.apply()
    }

    /**
     * Recupera la preferenza dell'utente per il tema.
     *
     * @param context Il contesto dell'applicazione o dell'attività.
     * @return Booleano che indica se il tema scuro è abilitato (true) o meno (false).
     * Di default, ritorna false (tema chiaro) se non è stata salvata nessuna preferenza.
     */
    fun getTheme(context: Context): Boolean {
        return getPreferences(context).getBoolean(THEME_KEY, false) // false = tema chiaro
    }
}

