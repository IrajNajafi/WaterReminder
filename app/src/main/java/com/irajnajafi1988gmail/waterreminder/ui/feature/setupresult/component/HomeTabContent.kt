@file:Suppress("DEPRECATION")

package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel.UserViewModel
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.model.ItemDishes
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.utils.WaterCalculatorDynamic
import com.irajnajafi1988gmail.waterreminder.ui.theme.DeepSkyBlue
import kotlinx.coroutines.delay

// ---------------------------
// 🔥 HOME SCREEN نهایی
// ---------------------------
@Composable
fun HomeTabContent(
    userViewModel: UserViewModel = hiltViewModel()
) {
    var selectedIcon by remember { mutableStateOf<Int?>(R.drawable.glass175) }
    var selectedVolume by remember { mutableStateOf(175) }
    var selectedVolumeLabel by remember { mutableStateOf("$selectedVolume ml") }
    var drunkWater by remember { mutableStateOf(0) }
    var showDishes by remember { mutableStateOf(false) }

    val userProfile by userViewModel.userProfile.collectAsState()
    val dailyWaterMl = remember(userProfile) {
        WaterCalculatorDynamic.calculateDailyNeedMl(userProfile)
    }

    // Lottie Animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.time))
    var playAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            playAnimation = true
            delay(4000)
            playAnimation = false
            delay(100)
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
                onClick = {},
                containerColor = Color.White
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.size(70.dp)
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

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
                        drunkWater in 1 until (dailyWaterMl * 0.95).toInt() ->
                            "Keep going, your daily goal is ${dailyWaterMl}ml"
                        drunkWater in (dailyWaterMl * 0.95).toInt()..(dailyWaterMl * 1.05).toInt() ->
                            "You reached your daily water goal!"
                        drunkWater > (dailyWaterMl * 1.05).toInt() ->
                            "You exceeded your daily water goal by ${drunkWater - dailyWaterMl}ml!"
                        else -> ""
                    },
                    color = when{
                        drunkWater == 0 -> Color(0xFF00A5FF)
                        drunkWater in 1 until (dailyWaterMl * 0.95).toInt() -> Color(0xFF00A5FF)
                        drunkWater in (dailyWaterMl * 0.95).toInt()..(dailyWaterMl * 1.05).toInt() -> Color(0xFF4CAF50)
                        drunkWater > (dailyWaterMl * 1.05).toInt() -> Color(0xFFDCE775)

                        else -> Color(0xFF00A5FF)
                    }
                )
            }

            Spacer(Modifier.height(20.dp))

            SemiCircleWithIconsAndCenterCircle(
                number = "$drunkWater / $dailyWaterMl ml",
                text = "${drunkWater / selectedVolume} / ${dailyWaterMl / selectedVolume} ",
                selectedIcon = selectedIcon,
                numberMl = selectedVolumeLabel,
                drunkWater = drunkWater,
                dailyWaterMl = dailyWaterMl,
                onClick = {
                    drunkWater += selectedVolume
                    if (drunkWater > dailyWaterMl) {
                        Log.d("HomeTabContent", "You drank more than daily goal!")
                    }
                }
            )

            WaterActionRow(
                onShowDishes = { showDishes = !showDishes },
                selectedIcon = selectedIcon,
                label = "$selectedVolume",

            )

            // Overlay تاریک وقتی Dishes باز است
            AnimatedVisibility(
                visible = showDishes,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { showDishes = false }
                )
            }
        }

        // Dishes Box
        if (showDishes) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { showDishes = false },
                contentAlignment = Alignment.Center
            ) {
                DishesBox(
                    onSelected = { iconId, volume, label ->
                        selectedIcon = iconId
                        selectedVolume = volume ?: 0
                        selectedVolumeLabel = label
                        showDishes = false
                    }
                )
            }
        }
    }
}

// ---------------------------
// Dishes Box
// ---------------------------
@Composable
fun DishesBox(
    modifier: Modifier = Modifier,
    onSelected: (icon: Int, volumeMl: Int?, label: String) -> Unit
) {
    val itemDishes = listOf(
        ItemDishes(R.drawable.cup100, "100 ml", 100),
        ItemDishes(R.drawable.cup125, "125 ml", 125),
        ItemDishes(R.drawable.glass175, "175 ml", 175),
        ItemDishes(R.drawable.mug200, "200 ml", 200),
        ItemDishes(R.drawable.thermos1000, "1000 ml", 1000),
        ItemDishes(R.drawable.cup_custom, "Custom", null)
    )

    Card(
        modifier = modifier.width(350.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemDishes.chunked(3).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowItems.forEach { item ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = item.label,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        onSelected(item.icon, item.volumeMl, item.label)
                                        Log.d(
                                            "DishesBox",
                                            "Icon clicked: ${item.label}, Volume: ${item.volumeMl}"
                                        )
                                    }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(item.label, fontSize = 12.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}



// ---------------------------
// Water Action Row
// ---------------------------
@Composable
fun WaterActionRow(
    onShowDishes: () -> Unit,
    selectedIcon: Int?,
    label: String,

    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) // ستون خالی سمت چپ

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.arrowup),
                contentDescription = null,
                modifier = Modifier
                    .rotate(-90f)
                    .size(20.dp),
                tint = DeepSkyBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Confirm that you have just drunk water",
                fontSize = 10.sp
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.refresh),
                contentDescription = "Show Dishes",
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onShowDishes() }
            )
            Spacer(modifier = Modifier.height(5.dp))
            selectedIcon?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$label ml ",  // ← نمایش ml و label زیر آیکون
                    fontSize = 10.sp
                )
            }
        }
    }
}
