package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.navigation.NavScreen
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.model.ItemDishes
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.viewModel.HomeViewModel

@Composable
fun  HomeTabContent(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    // 🌊 States از ویومدل
    val drunkWater by homeViewModel.dailyWaterDrunk.collectAsState()
    val dailyWaterGoal by homeViewModel.dailyWaterGoal.collectAsState()
    val selectedDish by homeViewModel.selectedDish.collectAsState()
    val showDishes by homeViewModel.showDishes.collectAsState()
    val showAlarm by homeViewModel.showAlarm.collectAsState()
    val alarmEnabled by homeViewModel.alarmEnabled.collectAsState()

    val selectedIcon = selectedDish.icon
    val selectedVolume = selectedDish.volumeMl ?: 0
    val selectedLabel = selectedDish.label

    // 🎬 Lottie animation آلارم
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.time))
    var playAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(composition, alarmEnabled) {
        if (alarmEnabled && composition != null) {
            while (true) {
                playAnimation = true
                kotlinx.coroutines.delay(4000)
                playAnimation = false
                kotlinx.coroutines.delay(100)
            }
        }
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = playAnimation,
        iterations = 1
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 20.dp),
                onClick = { homeViewModel.toggleAlarm() },
                containerColor = Color.White
            ) {
                composition?.let {
                    LottieAnimation(
                        composition = it,
                        progress = { progress },
                        modifier = Modifier.size(70.dp)
                    )
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 💧 Drop Image + Tooltip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.drop_image),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )

                Tooltip(
                    text = when {
                        drunkWater == 0 -> "Start drinking your first glass of water"
                        drunkWater < (dailyWaterGoal * 0.95).toInt() -> "Keep going, your daily goal is ${dailyWaterGoal} ml"
                        drunkWater <= (dailyWaterGoal * 1.05).toInt() -> "You reached your daily water goal!"
                        else -> "You exceeded your daily water goal by ${drunkWater - dailyWaterGoal} ml!"
                    },
                    color = when {
                        drunkWater < (dailyWaterGoal * 0.95).toInt() -> Color(0xFF00A5FF)
                        drunkWater <= (dailyWaterGoal * 1.05).toInt() -> Color(0xFF4CAF50)
                        else -> Color(0xFFDCE775)
                    }
                )
            }

            Spacer(Modifier.height(20.dp))

            // 🥤 SemiCircle + متن شمارش
            val cupsText = if (selectedVolume > 0) {
                "${drunkWater / selectedVolume} cups / ${dailyWaterGoal / selectedVolume} cups"
            } else {
                "0 cups / -"
            }

            SemiCircleWithIconsAndCenterCircle(
                number = "$drunkWater / $dailyWaterGoal ml",
                text = cupsText,
                selectedIcon = selectedIcon,
                numberMl = selectedLabel,
                drunkWater = drunkWater,
                dailyWaterMl = dailyWaterGoal,
                onClick = {
                    if (selectedVolume > 0) {
                        val newAmount = drunkWater + selectedVolume
                        homeViewModel.saveDailyWater(newAmount)
                    }
                }
            )

            Spacer(Modifier.height(20.dp))

            // 💧 Water Action Row
            WaterActionRow(
                onShowDishes = { homeViewModel.toggleDishes() },
                selectedIcon = selectedIcon,
                label = selectedLabel
            )

            Spacer(Modifier.height(20.dp))

            // 🔄 دکمه ریست کامل
            Button(onClick = { homeViewModel.resetAll() }) {
                Text("Reset All")
            }
        }

        // 🍽️ Overlay انتخاب لیوان
        if (showDishes) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { homeViewModel.toggleDishes() },
                contentAlignment = Alignment.Center
            ) {
                DishesBox(
                    itemDishes = homeViewModel.itemDishes,
                    onSelected = { icon, volumeMl, label ->
                        homeViewModel.selectDish(
                            ItemDishes(icon, label, volumeMl)
                        )
                        homeViewModel.toggleDishes()
                    }
                )
            }
        }

        // ⏰ Overlay آلارم
        if (showAlarm) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { homeViewModel.toggleAlarm() },
                contentAlignment = Alignment.Center
            ) {
                ItemAlarm(
                    onManualClick = { navController.navigate(NavScreen.ManualSettings.route) },
                    onAutomaticClick = { navController.navigate(NavScreen.AutomaticSettings.route) }
                )
            }
        }
    }

    // ♻️ اطمینان از بسته شدن Overlayها بعد از ریست
    LaunchedEffect(drunkWater) {
        if (drunkWater == 0) {
            if (showDishes) homeViewModel.toggleDishes()
            if (showAlarm) homeViewModel.toggleAlarm()
        }
    }
}
