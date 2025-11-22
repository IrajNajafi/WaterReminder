@file:Suppress("DEPRECATION")

package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.navigation.NavScreen
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.ActivityLevelStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.AgeStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.EnvironmentStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.GenderStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.ItemSetupPath
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.LoadingScreenWithWave
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.NavigationButtons
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.WeightStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ActivityLevel
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Gender
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ItemGender
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel.SetupViewModel
import com.irajnajafi1988gmail.waterreminder.ui.theme.Pink40
import kotlinx.coroutines.delay

@Composable
fun SetupScreen(
    viewModel: SetupViewModel = hiltViewModel(),
    navController: NavController
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val currentStep by viewModel.currentStep.collectAsState()
    val stepStatuses by viewModel.stepStatuses.collectAsState()
    val genderItems = viewModel.genderItems
    val isNextEnabled by viewModel.isNextEnabled.collectAsState()

    var showLoading by remember { mutableStateOf(false) }
    if (showLoading) {
        LoadingScreenWithWave()
        LaunchedEffect(showLoading) {
            delay(2000)
            navController.navigate(NavScreen.ResultScreen.route) {
                popUpTo(NavScreen.SetupScreen.route) { inclusive = true }
            }
        }
        return
    }

    Scaffold(
        bottomBar = {
            NavigationButtons(
                currentStep = currentStep,
                onNext = {
                    if (currentStep < 4) viewModel.setCurrentStep(currentStep + 1)
                    else showLoading = true
                },
                onBack = { if (currentStep > 0) viewModel.setCurrentStep(currentStep - 1) },
                isNextEnabled = isNextEnabled
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ItemSetupPath(steps = stepStatuses)
            Spacer(modifier = Modifier.height(16.dp))

            when (currentStep) {
                0 -> GenderStep(
                    items = genderItems,
                    selectedGender = userProfile.gender,
                    onSelect = { viewModel.setGender(it) },
                    modifier = Modifier.fillMaxSize()
                )

                1 -> WeightStep()
                2 -> AgeStep()
                3 -> ActivityLevelStep()
                4 -> EnvironmentStep()
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}



