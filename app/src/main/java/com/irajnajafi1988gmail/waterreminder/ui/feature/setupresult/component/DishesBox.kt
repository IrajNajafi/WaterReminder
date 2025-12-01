package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.model.ItemDishes

@Composable
fun DishesBox(
    itemDishes: List<ItemDishes>,
    onSelected: (icon: Int, volumeMl: Int?, label: String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .widthIn(min = 280.dp, max = 350.dp) // اندازه ثابت یا حداکثر
            .wrapContentHeight() // ارتفاع بر اساس محتوا
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemDishes.chunked(3).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowItems.forEach { item ->
                        Column(
                            modifier = Modifier
                                .padding(vertical = 6.dp)
                                .clickable { onSelected(item.icon, item.volumeMl, item.label) },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = item.label,
                                modifier = Modifier.size(42.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.label,
                                fontSize = 12.sp,
                                color = Color(0xFF333333)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}
