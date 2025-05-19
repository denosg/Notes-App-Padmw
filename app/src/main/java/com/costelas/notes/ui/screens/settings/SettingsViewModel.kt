package com.costelas.notes.ui.screens.settings

import androidx.lifecycle.ViewModel
import com.costelas.notes.data.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeManager: ThemeManager
) : ViewModel() {

    private val _isDarkMode = MutableStateFlow(themeManager.isDarkMode())
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun toggleTheme() {
        val newMode = !_isDarkMode.value
        themeManager.setDarkMode(newMode)
        _isDarkMode.value = newMode
    }
}