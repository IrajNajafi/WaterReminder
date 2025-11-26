@file:Suppress("DEPRECATION")

package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.irajnajafi1988gmail.waterreminder.R

@Composable
fun LoadingScreenWithWave() {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedWaveBackground()

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.progress))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            speed = 1.5f
        )

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.Center)
        )
    }
}