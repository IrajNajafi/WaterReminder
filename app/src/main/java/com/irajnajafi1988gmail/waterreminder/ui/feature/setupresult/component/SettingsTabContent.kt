package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.irajnajafi1988gmail.waterreminder.navigation.NavScreen
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel.SetupViewModel

@Composable
fun SettingsTabContent(
    navController: NavController,
    setupViewModel: SetupViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Settings Tab")

        Spacer(modifier = Modifier.height(24.dp))

        // 🔄 دکمه رفتن به SetupScreen برای ویرایش دوباره پروفایل
        Button(
            onClick = {
               setupViewModel .resetProfile()
                navController.navigate(NavScreen.SetupScreen.route) {
                    popUpTo(NavScreen.ResultScreen.route) { inclusive = true }
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.height(50.dp)
        ) {
            Text("ریست تنظیمات", fontSize = 18.sp)
        }

    }
}
