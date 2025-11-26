package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel.UserViewModel

@Composable
fun SettingsTabContent(
    userViewModel: UserViewModel = hiltViewModel(),
    navController: NavController

) {
    val userProfile by userViewModel.userProfile.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                userViewModel.resetUserProfile()
                navController.navigate("setup_screen") {
                    popUpTo("home_screen") { inclusive = true } // همه صفحه‌های قبل حذف شود
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset Profile")
        }
    }
}
