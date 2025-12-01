package com.irajnajafi1988gmail.waterreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.irajnajafi1988gmail.waterreminder.navigation.AppNavigation
import com.irajnajafi1988gmail.waterreminder.ui.theme.WaterReminderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaterReminderTheme {

           AppNavigation()
              //  HomeTabContent()
              //  HistoryTabContent()
            }
        }
    }
}


