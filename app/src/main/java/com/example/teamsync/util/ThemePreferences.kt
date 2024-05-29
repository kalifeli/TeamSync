package com.example.teamsync.util

// File: ThemePreferences.kt


import android.content.Context
import android.content.SharedPreferences

object ThemePreferences {
    private const val PREFERENCES_NAME = "theme_preferences"
    private const val THEME_KEY = "theme_key"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun saveTheme(context: Context, isDarkTheme: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(THEME_KEY, isDarkTheme)
        editor.apply()
    }

    fun getTheme(context: Context): Boolean {
        return getPreferences(context).getBoolean(THEME_KEY, false) // false = tema chiaro
    }
}

