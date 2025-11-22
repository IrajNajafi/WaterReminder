package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@Composable
fun AnimatedWaveBackground() {
    val infiniteTransition = rememberInfiniteTransition()

    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing)
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height


        drawRect(
            color = Color(0xFF2196F3),
            size = size
        )

        val waveHeight = 50f
        val waveLength = 300f

        val path = Path().apply {
            moveTo(0f, height / 2)
            var x = -waveLength + waveOffset
            while (x < width) {
                quadraticBezierTo(
                    x + waveLength / 4, height / 2 - waveHeight,
                    x + waveLength / 2, height / 2
                )
                quadraticBezierTo(
                    x + 3 * waveLength / 4, height / 2 + waveHeight,
                    x + waveLength, height / 2
                )
                x += waveLength
            }
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(
            path = path,
            color = Color(0xFF64B5F6)
        )
    }
}
