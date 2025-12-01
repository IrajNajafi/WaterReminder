package com.irajnajafi1988gmail.waterreminder.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.LoadingScreenWithWave
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.screen.SetupScreen
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel.SetupViewModel
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.screen.AutomaticSettings
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.screen.ManualSettings
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.screen.ThreeTabsScreen
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.viewModel.HomeViewModel


@Composable
fun AppNavigation(userViewModel: SetupViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val isProfileComplete by userViewModel.isProfileCompleteState.collectAsState()

    if (isProfileComplete == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LoadingScreenWithWave()
        }
        return
    }

    NavHost(
        navController = navController,
        startDestination = if (isProfileComplete == true) NavScreen.ResultScreen.route
        else NavScreen.SetupScreen.route
    ) {
        composable(NavScreen.SetupScreen.route) {
            SetupScreen(
                navController = navController,
                viewModel = userViewModel,
                onComplete = {
                    userViewModel.completeProfile() // ذخیره پروفایل در دیتاستور
                    navController.navigate(NavScreen.ResultScreen.route) {
                        popUpTo(NavScreen.SetupScreen.route) { inclusive = true }
                    }
                }
            )
        }

        composable(NavScreen.ResultScreen.route) {
            ThreeTabsScreen(navController)
        }

        composable(NavScreen.AutomaticSettings.route) {
            AutomaticSettings()
        }

        composable(NavScreen.ManualSettings.route) {
            ManualSettings()
        }
    }
}
