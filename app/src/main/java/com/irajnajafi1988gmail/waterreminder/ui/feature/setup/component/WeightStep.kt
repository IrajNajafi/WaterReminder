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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.viewmodel.SetupViewModel

@Composable
fun WeightStep(
    viewModel: SetupViewModel= hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val weight by viewModel.userProfile.collectAsState().let { state ->
        derivedStateOf { state.value.weight ?: 50 }
    }

    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Please Select Your Weight.",
            fontSize = 25.sp,
            fontWeight = FontWeight.Light,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.weight_lifting_icon),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
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
                            value = weight

                            setOnValueChangedListener { _, _, newVal ->
                                viewModel.setWeight(newVal)
                            }
                        }
                    },
                    update = { picker ->
                        picker.value = weight
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Kg",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
