package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.ActivityLevelStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.AgeStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.EnvironmentStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.GenderStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.ItemSetupPath
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.WeightStep
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ActivityLevel
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Gender
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ItemGender
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel.SetupViewModel
import com.irajnajafi1988gmail.waterreminder.ui.theme.Pink40

@Composable
fun SetupScreen(
    viewModel: SetupViewModel = hiltViewModel(),
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val currentStep by viewModel.currentStep.collectAsState()
    val stepStatuses by viewModel.stepStatuses.collectAsState()

    val imagesGender = remember {
        listOf(
            ItemGender(R.drawable.man_male, Gender.MALE.name, Gender.MALE),
            ItemGender(R.drawable.woman_female, Gender.FEMALE.name, Gender.FEMALE)
        )
    }

    val isNextEnabled = when (currentStep) {
        0 -> userProfile.gender != null
        1 -> (userProfile.weight ?: 0) > 0
        2 -> (userProfile.age ?: 0) > 0
        3 -> userProfile.activity != null
        4 -> userProfile.environment != null
        else -> true
    }


    Scaffold(
        bottomBar = {
            NavigationButtons(
                currentStep = currentStep,
                onNext = { if (currentStep < 4) viewModel.setCurrentStep(currentStep + 1) },
                onBack = { if (currentStep > 0) viewModel.setCurrentStep(currentStep - 1) },
                isNextEnabled = isNextEnabled
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // مسیر مراحل
            ItemSetupPath(steps = stepStatuses)
            Spacer(modifier = Modifier.height(16.dp))

            // نمایش محتوا بر اساس مرحله
            when (currentStep) {
                0 -> GenderStep(
                    items = imagesGender,
                    selectedGender = userProfile.gender,
                    onSelect = { viewModel.setGender(it) },
                    modifier = Modifier.fillMaxSize()
                )

                1 -> WeightStep()
                2 -> AgeStep()
                3 -> ActivityLevelStep()
                4->EnvironmentStep()

            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun NavigationButtons(
    currentStep: Int,
    onNext: () -> Unit,
    onBack: () -> Unit,
    isNextEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onBack,
            enabled = currentStep > 0,
            shape = CircleShape,
            modifier = Modifier.size(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Pink40,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Button(
            onClick = onNext,
            enabled = isNextEnabled,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Pink40,
                contentColor = Color.White
            ),
        ) {
            Text(
                text = if (currentStep < 4) "Next" else "Finish",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
