package com.costelas.notes.ui.navigation

sealed class Screens(val route: String, val title: String) {
    object Home : Screens("home_screen_route", "Notes")
    object Edit : Screens("add_edit_screen_route", "Add/Edit")
    object Settings : Screens("settings", "Settings")
    object DailyInspiration : Screens("daily_inspiration_route", "Inspiration")
}