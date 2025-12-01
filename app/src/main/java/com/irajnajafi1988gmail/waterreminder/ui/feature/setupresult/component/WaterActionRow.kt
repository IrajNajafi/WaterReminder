package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irajnajafi1988gmail.waterreminder.R
import com.irajnajafi1988gmail.waterreminder.ui.theme.DeepSkyBlue

@Composable
fun WaterActionRow(
    onShowDishes: () -> Unit,
    selectedIcon: Int,
    label: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // خالی برای بالانس راست و چپ
        Box(modifier = Modifier.weight(1f))

        // 🔵 ستون وسط (پیام نوشیدن آب)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.arrowup),
                contentDescription = null,
                modifier = Modifier
                    .rotate(-90f)
                    .size(22.dp),
                tint = DeepSkyBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Confirm that you have just drunk water",
                fontSize = 11.sp,
                color = Color.DarkGray
            )
        }

        // 🔵 ستون راست (رفرش + آیکون لیوان)
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            // دکمه باز شدن دیش‌ها
            Image(
                painter = painterResource(R.drawable.refresh),
                contentDescription = "Show Dishes",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onShowDishes() }
            )

            Spacer(modifier = Modifier.height(6.dp))

            // آیکون لیوان انتخاب‌شده
            Icon(
                painter = painterResource(selectedIcon),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(2.dp))

            // لیبل (مثلاً "175 ml" یا "Custom")
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.DarkGray
            )
        }
    }
}
