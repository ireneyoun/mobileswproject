package com.example.snowtimerapp.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.snowtimerapp.ui.components.BottomNavBar
import kotlinx.coroutines.delay

@Composable
fun TimerPlayScreen(
    itemId: String,
    navController: NavHostController,
    timerViewModel: TimerViewModel = viewModel()
) {
    LaunchedEffect(itemId) {
        timerViewModel.startTimer(itemId)
    }

    val totalSeconds by timerViewModel.currentRunningTime.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                Column {
                    HorizontalDivider(color = Color.LightGray)
                    BottomNavBar(
                        currentRoute = "",
                        navController = navController
                    )
                }
            },
            containerColor = Color.White,
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatTime(totalSeconds),
                    style = MaterialTheme.typography.titleLarge
                )

                IconButton(onClick = {
                    timerViewModel.stopTimer(itemId)
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.PauseCircleFilled,
                        contentDescription = "정지"
                    )
                }

            }
        }
    }
}