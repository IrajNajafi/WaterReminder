package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component

import android.support.v4.os.IResultReceiver
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel.SetupViewModel

@Composable
fun AgeStep(
    viewModel: SetupViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val age by viewModel.userProfile.collectAsState().let { state ->
        derivedStateOf { state.value.weight ?: 25 }
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.time))

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Please Select Your Age.",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(220.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AndroidView(
                    modifier = Modifier
                        .height(170.dp)
                        .width(100.dp),
                    factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 1
                            maxValue = 120
                            value = age
                            setOnValueChangedListener { _, _, newVal ->
                                viewModel.setAge(newVal)
                            }

                        }
                    },
                    update = { picker ->
                        picker.value = age
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Year",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

            }

        }
        Spacer(modifier = Modifier.weight(1f))
    }
}