package com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.navigation.NavController
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component.HistoryTabContent
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component.HomeTabContent
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.component.SettingsTabContent
import com.irajnajafi1988gmail.waterreminder.ui.feature.setupresult.model.TableSection
import com.irajnajafi1988gmail.waterreminder.ui.theme.DeepSkyBlue
import com.irajnajafi1988gmail.waterreminder.ui.theme.SkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreeTabsScreen(
    navController: NavController
) {

    val tabs = TableSection.entries

    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // --- Tabs ---
        TabRow(
            selectedTabIndex = selectedTab,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = Color.White
                )
            }

        ) {
            tabs.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .background(SkyBlue)
                        .padding(vertical = 20.dp),
                    Alignment.Center
                ) {
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(item.icon),
                                    contentDescription = item.label,
                                    modifier = Modifier.size(25.dp),
                                    tint = if (item.icon == TableSection.HOME.icon) DeepSkyBlue else Color.White


                                )
                                Spacer(modifier = Modifier.padding(6.dp))
                                Text(
                                    text = item.label,
                                    color = Color.White
                                )
                            }
                        }
                    )
                }

            }
        }

        // --- Content of Tabs ---
        when (selectedTab) {
            0 -> HomeTabContent(navController = navController)
            1 -> HistoryTabContent()
            2 -> SettingsTabContent(navController=navController)
        }
    }
}
