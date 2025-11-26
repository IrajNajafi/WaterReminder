package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Tooltip(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color =  Color(0xFF444242),
    color: Color
) {
    Surface(
        color = backgroundColor,
        shape = tooltipShape(
            arrowSize = 30f,
            cornerRadius = 30f,
            arrowOffsetY = 40f
        ),
        modifier = modifier.padding(start = 10.dp)
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color =color,
                fontSize =10.sp
            )
        }
    }
}
fun tooltipShape(
    arrowSize: Float = 30f,
    cornerRadius: Float = 20f,
    arrowOffsetY: Float = 40f
): Shape = object : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val path = Path().apply {

            moveTo(arrowSize, 0f)

            lineTo(size.width - cornerRadius, 0f)
            arcTo(
                Rect(
                    Offset(size.width - cornerRadius, 0f),
                    Size(cornerRadius, cornerRadius)
                ),
                -90f,
                90f,
                false
            )

            lineTo(size.width, size.height - cornerRadius)
            arcTo(
                Rect(
                    Offset(size.width - cornerRadius, size.height - cornerRadius),
                    Size(cornerRadius, cornerRadius)
                ),
                0f,
                90f,
                false
            )

            lineTo(arrowSize + cornerRadius, size.height)
            arcTo(
                Rect(
                    Offset(arrowSize, size.height - cornerRadius),
                    Size(cornerRadius, cornerRadius)
                ),
                90f,
                90f,
                false
            )

            lineTo(arrowSize, arrowOffsetY + arrowSize)
            lineTo(0f, arrowOffsetY + arrowSize / 2)
            lineTo(arrowSize, arrowOffsetY)

            close()
        }

        return Outline.Generic(path)
    }
}
