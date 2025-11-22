package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.theme.Pink40

@Composable
fun NavigationButtons(
    currentStep: Int,
    onNext: () -> Unit,
    onBack: () -> Unit,
    isNextEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onBack,
            enabled = currentStep > 0,
            shape = CircleShape,
            modifier = Modifier.size(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Pink40,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Button(
            onClick = onNext,
            enabled = isNextEnabled,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Pink40,
                contentColor = Color.White
            ),
        ) {
            Text(
                text = if (currentStep < 4) "Next" else "Finish",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

