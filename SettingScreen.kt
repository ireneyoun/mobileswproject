package com.example.snowtimerapp.ui.screens.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.snowtimerapp.ui.components.BottomNavBar
import com.example.snowtimerapp.ui.components.MyTopAppBar

@Composable
fun SettingScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            Column {
                MyTopAppBar(title = "설정")
                HorizontalDivider(color = Color.LightGray)
            }
        },
        bottomBar = {
            Column {
                HorizontalDivider(color = Color.LightGray)
                BottomNavBar(
                    currentRoute = "settings",
                    navController = navController
                )
            }
        },
        containerColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        var switchOn by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Switch(
                checked = switchOn,
                onCheckedChange = { switchOn = it }
            )
        }
    }
}
