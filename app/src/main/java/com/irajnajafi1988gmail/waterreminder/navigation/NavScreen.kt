package com.irajnajafi1988gmail.waterreminder.navigation

sealed class NavScreen(val route: String) {

    object SetupScreen : NavScreen("setup_screen")
    object ResultScreen : NavScreen("result_screen")
    object ManualSettings : NavScreen("manual_settings")
    object AutomaticSettings : NavScreen("automatic_settings")
}
