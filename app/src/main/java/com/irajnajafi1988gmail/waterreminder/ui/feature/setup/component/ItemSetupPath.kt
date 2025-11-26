package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.StepStatus
import com.irajnajafi1988gmail.waterreminder.ui.theme.BrightOrange
import com.irajnajafi1988gmail.waterreminder.ui.theme.DeepSkyBlue
import com.irajnajafi1988gmail.waterreminder.ui.theme.LightSkyBlue
import com.irajnajafi1988gmail.waterreminder.ui.theme.PurpleGrey40

// -------------------------------------------------
// مسیر مراحل با پیکان و خط متحرک
// -------------------------------------------------
@Composable
fun ItemSetupPath(
    modifier: Modifier = Modifier,
    steps: List<StepStatus>,
    currentStep: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        steps.forEachIndexed { index, step ->
            val isActive = index <= currentStep

            ItemPath(
                icon = step.icon,
                label = step.label,
                color = if (isActive) step.color else Color.LightGray
            )

            if (index < steps.lastIndex) {
                StepProgressLine(
                    color =if (isActive) step.color else Color.LightGray,
                    progress = if (index < currentStep) 1f else 0f, // مرحله قبل کامل
                    modifier = Modifier
                        .weight(1f)
                        .height(3.dp)
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}

// -------------------------------------------------
// آیتم تک مرحله با پیکان
// -------------------------------------------------
@Composable
fun ItemPath(
    icon: Int,
    label: String,
    color: Color,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 50.dp, height = 28.dp)
                .clip(arrowShape())
                .background(color)
                .border(2.dp, BrightOrange, arrowShape()),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(15.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color =DeepSkyBlue,
        )
    }
}
@Composable
fun StepProgressLine(
    modifier: Modifier = Modifier,
    color: Color = Color.Blue,
    progress: Float // 0f تا 1f
) {
    // Animate progress smoothly when it changes
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = androidx.compose.animation.core.tween(
            durationMillis = 500
        )
    )

    Canvas(modifier = modifier.height(6.dp)) {
        val lineWidth = size.width * animatedProgress
        drawLine(
            color = color,
            start = Offset(0f, size.height / 2),
            end = Offset(lineWidth, size.height / 2),
            strokeWidth = size.height,
            cap = StrokeCap.Round
        )
    }
}


// -------------------------------------------------
// شکل پیکان
// -------------------------------------------------
fun arrowShape() = GenericShape { size, _ ->
    moveTo(0f, 0f)
    lineTo(size.width * 0.8f, 0f)
    lineTo(size.width, size.height / 2)
    lineTo(size.width * 0.8f, size.height)
    lineTo(0f, size.height)
    close()
}
