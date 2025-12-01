@file:Suppress("DEPRECATION")

package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ActivityLevel
import com.irajnajafi1988gmail.waterreminder.ui.theme.PurpleGrey40

@Composable
fun ActivityLevelStep(
    selectedActivity: ActivityLevel?,
    onActivityChange: (ActivityLevel) -> Unit,
    modifier: Modifier = Modifier,
) {
   val finalActivity = selectedActivity?: ActivityLevel.LOW

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.activity))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Please Select Your Activity Level.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleGrey40
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(220.dp)

            )
            LevelRadioGroup(
                selectedOption = finalActivity,
                onSelectedChange = { onActivityChange(it) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
fun LevelRadioGroup(
    selectedOption: ActivityLevel,
    onSelectedChange: (ActivityLevel) -> Unit
) {
    val option = listOf(
        ActivityLevel.LOW,
        ActivityLevel.MEDIUM,
        ActivityLevel.HIGH,
        ActivityLevel.EXTREME
    )
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Select Level:",
            style = MaterialTheme.typography.titleMedium
        )
        option.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onSelectedChange(option) }
                )
                Text(
                    text = option.name,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

}