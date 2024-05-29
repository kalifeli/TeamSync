package com.example.teamsync.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamsync.util.ThemePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(context: Context) : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(ThemePreferences.getTheme(context))
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun setTheme(isDark: Boolean, context: Context) {
        _isDarkTheme.value = isDark
        viewModelScope.launch {
            ThemePreferences.saveTheme(context, isDark)
        }
    }
}
