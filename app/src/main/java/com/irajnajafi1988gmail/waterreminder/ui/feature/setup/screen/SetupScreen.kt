@file:Suppress("DEPRECATION")

package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.irajnajafi1988gmail.waterreminder.navigation.NavScreen
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.ActivityLevelStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.AgeStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.EnvironmentStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.GenderStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.ItemSetupPath
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.LoadingScreenWithWave
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.NavigationButtons
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.WeightStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel.SetupViewModel
import kotlinx.coroutines.delay


@Composable
fun SetupScreen(
    viewModel: SetupViewModel = hiltViewModel(),
    navController: NavController,
    onComplete: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val currentStep by viewModel.currentStep.collectAsState()
    val stepStatuses by viewModel.stepStatuses.collectAsState()
    val genderItems = viewModel.genderItems
    val isNextEnabled by viewModel.isNextEnabled.collectAsState()

    Scaffold(
        bottomBar = {
            if (currentStep < 5) {
                NavigationButtons(
                    currentStep = currentStep,
                    isNextEnabled = isNextEnabled,
                    onBack = { if (currentStep > 0) viewModel.setCurrentStep(currentStep - 1) },
                    onNext = {
                        val isLastStep = currentStep == 4
                        if (isLastStep) {
                            // ذخیره پروفایل و رفتن به صفحه بعد
                            onComplete()
                        } else {
                            viewModel.setCurrentStep(currentStep + 1)
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentStep < 5) {
                ItemSetupPath(steps = stepStatuses, currentStep = currentStep)
                Spacer(modifier = Modifier.height(16.dp))
            }

            when (currentStep) {
                0 -> GenderStep(
                    items = genderItems,
                    selectedGender = userProfile.gender,
                    onSelect = { viewModel.setGender(it) },
                    modifier = Modifier.fillMaxSize()
                )
                1 -> WeightStep(
                    weight = userProfile.weight,
                    onWeightChange = { viewModel.setWeight(it) }
                )
                2 -> AgeStep(
                    age = userProfile.age,
                    onAgeChange = { viewModel.setAge(it) }
                )
                3 -> ActivityLevelStep(
                    selectedActivity = userProfile.activity,
                    onActivityChange = { viewModel.setActivity(it) }
                )
                4 -> EnvironmentStep(
                    selectedEnvironment = userProfile.environment,
                    onEnvironmentChange = { viewModel.setEnvironment(it) }
                )
            }

            if (currentStep < 5) Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
