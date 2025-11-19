package com.irajnajafi1988gmail.waterreminder.ui.feature.setup.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.Gender
import com.irajnajafi1988gmail.waterreminder.ui.feature.setup.model.ItemGender
import androidx.compose.ui.text.font.FontWeight

@Composable
fun GenderStep(
    items: List<ItemGender>,
    selectedGender: Gender?,
    onSelect: (Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Please select your gender.",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                ItemGenderCard(
                    image = item.image,
                    label = item.label,
                    isSelected = selectedGender == item.gender,
                    onClick = { onSelect(item.gender) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ItemGenderCard(
    image: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val alphaValue = if (isSelected) 1f else 0.3f
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = label,
            modifier = Modifier
                .size(150.dp)
                .alpha(alphaValue)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray.copy(alpha = alphaValue)
        )
    }
}
