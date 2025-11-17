package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.StepStatus
import com.irajnajafi1988gmail.waterreminder.ui.theme.BrightOrange
import com.irajnajafi1988gmail.waterreminder.ui.theme.DeepSkyBlue

@Composable
fun ItemSetupPath(
    steps: List<StepStatus>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            ItemPath(step)
            if (index < steps.lastIndex) {
                AnimatedDashedLine(color = if (step.isActive) step.color else Color.LightGray)
            }
        }
    }
}

@Composable
fun ItemPath(step: StepStatus) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(50.dp, 28.dp)
                .clip(arrowShape())
                .background(if (step.isActive) step.color else Color.LightGray)
                .border(2.dp, BrightOrange, arrowShape()),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(step.icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(15.dp)
            )
        }

        Text(
            text = step.label,
            fontSize = 10.sp,
            color = DeepSkyBlue
        )
    }
}

@Composable
fun AnimatedDashedLine(color: Color) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Text(
        text = "---",
        color = color.copy(alpha = alpha),
        fontSize = 16.sp
    )
}


fun arrowShape() = GenericShape { size, _ ->
    moveTo(0f, 0f)
    lineTo(size.width * 0.8f, 0f)
    lineTo(size.width, size.height / 2)
    lineTo(size.width * 0.8f, size.height)
    lineTo(0f, size.height)
    close()
}
