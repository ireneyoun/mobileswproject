package com.example.snowtimerapp.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var isGroupSelected by remember { mutableStateOf(true) }
    val studyItems by timerViewModel.studyItems.collectAsState()

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "총 공부 시간",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Text(
                                text = formatTime(totalSeconds),
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )

                            IconButton(onClick = {
                                timerViewModel.stopTimer(itemId)
                                navController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.PauseCircleFilled,
                                    contentDescription = "정지",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { isGroupSelected = true },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = if (isGroupSelected) Color.Black else Color.LightGray
                        ),
                        modifier = Modifier
                            .width(130.dp)
                            .height(55.dp)
                            .drawBehind {
                                if (isGroupSelected) {
                                    val startX = 0f
                                    val endX = size.width
                                    val y = size.height - 3.dp.toPx() / 2

                                    drawLine(
                                        color = Color.Black,
                                        start = Offset(startX, y),
                                        end = Offset(endX, y),
                                        strokeWidth = 3.dp.toPx()
                                    )
                                }
                            }
                    ) {
                        Text(
                            text = "과목별",
                            fontSize = 20.sp
                        )
                    }

                    Button(
                        onClick = { isGroupSelected = false },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = if (!isGroupSelected) Color.Black else Color.LightGray
                        ),
                        modifier = Modifier
                            .width(130.dp)
                            .height(55.dp)
                            .drawBehind {
                                if (!isGroupSelected) {
                                    val startX = 0f
                                    val endX = size.width
                                    val y = size.height - 3.dp.toPx() / 2

                                    drawLine(
                                        color = Color.Black,
                                        start = Offset(startX, y),
                                        end = Offset(endX, y),
                                        strokeWidth = 3.dp.toPx()
                                    )
                                }
                            }
                    ) {
                        Text(
                            text = "그룹별",
                            fontSize = 20.sp
                        )
                    }
                }

                HorizontalDivider(color = Color.LightGray)

                AnimatedContent(
                    targetState = isGroupSelected,
                    transitionSpec = {
                        if (targetState) {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(400)
                            ) togetherWith
                                    slideOutHorizontally(
                                        targetOffsetX = { it },
                                        animationSpec = tween(400)
                                    )
                        } else {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(400)
                            ) togetherWith
                                    slideOutHorizontally(
                                        targetOffsetX = { -it },
                                        animationSpec = tween(400)
                                    )
                        }
                    },
                    label = "Tab Animation",
                    contentAlignment = Alignment.TopStart
                ) { showTimer ->
                    if (showTimer) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            items(
                                items = studyItems,
                                key = { item -> item.title }
                            ) { item ->
                                StudyTimerItem(
                                    item = item,
                                    onPlay = null,
                                    onNavigateGroup = {
                                        navController.navigate("group_select/${item.title}")
                                    }
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            TimerGroupScreen()
                        }
                    }
                }
            }
        }
    }
}