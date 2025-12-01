package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.irajnajafi1988gmail.waterreminder.R

@Composable
fun ItemAlarm(
    onAutomaticClick: () -> Unit = {},
    onManualClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // عنوان
            Text(
                text = "Choose Alarm Mode",
                fontSize = 17.sp,
                color = Color(0xFF222222)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ---------------------------------------------------
            // 🔵 گزینه اتوماتیک
            // ---------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAutomaticClick() }
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.automatic_setting),
                    contentDescription = "Automatic Alarm",
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Automatic settings",
                        fontSize = 15.sp,
                        color = Color(0xFF222222)
                    )
                    Text(
                        text = "Smart schedule based on activity",
                        fontSize = 12.sp,
                        color = Color(0xFF777777)
                    )
                }
            }

            Divider(color = Color.Gray.copy(alpha = 0.2f), thickness = 1.dp)

            // ---------------------------------------------------
            // 🟠 گزینه دستی
            // ---------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onManualClick() }
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.hand),
                    contentDescription = "Manual Alarm",
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "Manual settings",
                        fontSize = 15.sp,
                        color = Color(0xFF222222)
                    )
                    Text(
                        text = "Set reminders manually",
                        fontSize = 12.sp,
                        color = Color(0xFF777777)
                    )
                }
            }
        }
    }
}
