package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.irajnajafi1988gmail.waterreminder.R

@Composable
fun HistoryTabContent() {
    var selectedIcon by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // اگر آیکونی انتخاب شد، بالای صفحه فقط همون نمایش داده میشه
        selectedIcon?.let {
            Icon(
                painter = painterResource(it),
                contentDescription = null,
                modifier = Modifier.size(80.dp) // سایز بزرگ برای نمایش تکی
            )
            Spacer(Modifier.height(20.dp))
        }

        // آیکون‌های ردیف پایین
        Row {
            val icons = listOf(R.drawable.cup125, R.drawable.cup100, R.drawable.person)
            icons.forEach { iconRes ->
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { selectedIcon = iconRes }
                )
                Spacer(Modifier.width(20.dp))
            }
        }
    }
}
