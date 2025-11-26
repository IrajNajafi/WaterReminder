package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irajnajafi1988gmail.waterreminder.R

@Composable
fun SemiCircleWithIconsAndCenterCircle(
    number: String,
    text: String,
    selectedIcon: Int?,
    numberMl: String,
    drunkWater: Int,
    dailyWaterMl: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {

        val size = 300.dp
        val iconOffsetY = 25.dp

        // تعیین وضعیت رنگی
        val state = when {
            drunkWater == 0 ->"start"
            drunkWater in 1 until (dailyWaterMl * 0.95).toInt()  -> "normal"
            drunkWater in (dailyWaterMl * 0.95).toInt()..(dailyWaterMl * 1.05).toInt() -> "goal"
            else -> "over"
        }

        // رنگ های حالت نیم‌دایره
        val progressColor = when (state) {

            "normal" -> Color(0xFF00A5FF)
            "goal" -> Color(0xFF4CAF50)
            "over" -> Color(0xFFDCE775)
            else -> Color(0xFF00A5FF)
        }

        // درصد پیشرفت
        val targetProgress =
            if (dailyWaterMl > 0) drunkWater.toFloat() / dailyWaterMl.toFloat() else 0f
        val progress by animateFloatAsState(
            targetValue = targetProgress.coerceAtMost(1f),
            animationSpec = androidx.compose.animation.core.tween(600)
        )

        // کشیدن نیم‌دایره
        Canvas(modifier = Modifier.size(size)) {
            val stroke = 12.dp.toPx()

            // خط پس‌زمینه
            drawArc(
                color = Color.LightGray,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(stroke)
            )

            // پیشرفت
            drawArc(
                color = progressColor,
                startAngle = 180f,
                sweepAngle = 180f * progress,
                useCenter = false,
                style = Stroke(stroke)
            )
        }

        // آیکون قطره چپ
        Icon(
            painter = painterResource(R.drawable.drop),
            contentDescription = null,
            modifier = Modifier
                .offset(x = -(size / 2), y = iconOffsetY)
                .size(32.dp),
            tint = Color(0xFF00A5FF)
        )

        // آیکون شخص راست
        Icon(
            painter = painterResource(R.drawable.person),
            contentDescription = null,
            modifier = Modifier
                .offset(x = (size / 2), y = iconOffsetY)
                .size(40.dp),
            tint = progressColor
        )

        // دکمه دایره وسط
        CircleButton(
            number = number,
            text = text,
            selectedIcon = selectedIcon,
            numberMl = numberMl,
            size = 250.dp,
            state = state,
            onClick = onClick
        )
    }
}

@Composable
fun CircleButton(
    number: String,
    text: String,
    numberMl: String,
    selectedIcon: Int?,
    size: Dp = 250.dp,
    state: String,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.94f else 1f)

    val centerBg = when (state) {

        "normal" -> Color(0xFFD6E7FD)
        "goal" -> Color(0xFFE8F5E9)
        "over" -> Color(0xFFFFFDE7)
        else -> Color.White
    }

    val gradientColors = when (state) {

        "normal" -> listOf(Color(0xFFABCFF6), Color(0xFFD6E7FD))
        "goal" -> listOf(Color(0xFFB2DFDB), Color(0xFFE8F5E9))
        "over" -> listOf(Color(0xFFF0F4C3), Color(0xFFFFFDE7))
        else -> listOf(Color.White, Color.LightGray)
    }

    Surface(
        modifier = Modifier
            .size(size)
            .scale(scale),
        shape = CircleShape,
        color = centerBg,
        border = BorderStroke(2.dp, Color.LightGray),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.radialGradient(
                        colors = gradientColors,
                        radius = size.value
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(number, color = Color.Black, fontSize = 22.sp)
                Text(text, color = Color.DarkGray, fontSize = 14.sp)

                selectedIcon?.let {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(numberMl, color = Color.Black, fontSize = 14.sp)
                }
            }
        }
    }
}
