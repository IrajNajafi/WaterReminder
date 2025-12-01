package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component

import android.widget.NumberPicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.theme.PurpleGrey40

@Composable
fun WeightStep(
    weight: Int?,
    onWeightChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val finalWeight = weight?.coerceIn(50, 400) ?: 50



    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Please Select Your Weight.",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleGrey40
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.weight2),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
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
                            maxValue = 400
                            value = finalWeight

                            setOnValueChangedListener { _, _, newVal ->
                                onWeightChange(newVal)
                            }
                        }
                    },
                    update = { picker ->
                        picker.value = finalWeight
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Kg",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
