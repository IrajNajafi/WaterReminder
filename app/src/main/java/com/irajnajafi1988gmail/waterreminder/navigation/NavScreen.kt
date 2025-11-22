package com.irajnajafi1988gmail.waterreminder.navigation

sealed class NavScreen(val route: String) {

    object SetupScreen : NavScreen("setup_screen")
    object ResultScreen : NavScreen("result_screen")
    object HomeScreen : NavScreen("home_screen")
    object HistoryScreen : NavScreen("history_screen")
    object SettingsScreen : NavScreen("settings_screen")
}
