package com.irajnajafi1988gmail.waterreminder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.screen.SetupScreen
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.screen.ThreeTabsScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavScreen.SetupScreen.route
    ) {

        composable(route = NavScreen.SetupScreen.route) {
            SetupScreen(navController = navController)
        }

        composable(route = NavScreen.ResultScreen.route) {
            ThreeTabsScreen()
        }

    }
}
