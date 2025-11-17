package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component.ItemSetupPath
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.*
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel.SetupViewModel

@Composable
fun SetupScreen(
    viewModel: SetupViewModel = hiltViewModel(),
    modifier: Modifier
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val currentStep by viewModel.currentStep.collectAsState()
    val stepStatuses by viewModel.stepStatuses.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // نمایش مسیر مراحل
        ItemSetupPath(steps = stepStatuses)

        Spacer(modifier = Modifier.height(16.dp))

        // نمایش محتوا بر اساس مرحله فعلی
        when (currentStep) {
          /*  0 -> GenderStep(userProfile.gender) { viewModel.setGender(it) }
            1 -> WeightStep(userProfile.weight) { viewModel.setWeight(it) }
            2 -> AgeStep(userProfile.age) { viewModel.setAge(it) }
            3 -> ActivityStep(userProfile.activity) { viewModel.setActivity(it) }
            4 -> EnvironmentStep(userProfile.environment) { viewModel.setEnvironment(it) }*/
        }

        Spacer(modifier = Modifier.height(24.dp))

        // دکمه ادامه / مرحله بعد
        Button(
            onClick = {
                if (currentStep < 4) viewModel.setCurrentStep(currentStep + 1)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (currentStep < 4) "Next" else "Finish")
        }
    }
}
