package com.example.snowtimerapp.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.snowtimerapp.ui.components.BottomNavBar
import com.example.snowtimerapp.ui.components.FloatingButton
import kotlinx.coroutines.delay

//data class StudyItem(
//    val title: String,
//    var isRunning: Boolean = false,
//    var seconds: Int = 0,
//    var todos: MutableList<String> = mutableListOf()
//)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeTimerScreen(
    navController: NavHostController,
    timerViewModel: TimerViewModel = viewModel()
) {
//    var studyItems by remember {
//        mutableStateOf(
//            listOf(
//                StudyItem("알고리즘"),
//                StudyItem("컴퓨터네트워크"),
//                StudyItem("데이터베이스셜계와질의"),
//                StudyItem("모바일소프트웨어"),
//                StudyItem("소프트웨어공학"),
//                StudyItem("딥러닝개론")
//            )
//        )
//    }

    var studyItems = timerViewModel.studyItems
    val totalSeconds = studyItems.sumOf { it.seconds }
    var isTimerSelected by remember { mutableStateOf(true) }

//    LaunchedEffect(studyItems) {
//        while (true) {
//            delay(1000L)
//            studyItems = studyItems.map {
//                if (it.isRunning)
//                    it.copy(seconds = it.seconds + 1)
//                else
//                    it
//            }
//        }
//        timerViewModel.updateTotalSeconds(studyItems.sumOf { it.seconds })
//    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"
    var bottomPaddingValue by remember { mutableStateOf(0.dp) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                Column {
                    HorizontalDivider(color = Color.LightGray)
                    BottomNavBar(
                        currentRoute = currentRoute,
                        navController = navController
                    )
                }
            },
            containerColor = Color.White,
             modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            bottomPaddingValue = paddingValues.calculateBottomPadding()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .height(150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "총 공부 시간",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = formatTime(totalSeconds),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                HorizontalDivider(color = Color.LightGray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { isTimerSelected = true },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = if (isTimerSelected) Color.Black else Color.LightGray
                        ),
                        modifier = Modifier
                            .width(130.dp)
                            .height(55.dp)
                            .drawBehind {
                                if (isTimerSelected) {
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
                            text = "타이머",
                            fontSize = 20.sp
                        )
                    }

                    Button(
                        onClick = { isTimerSelected = false },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = if (!isTimerSelected) Color.Black else Color.LightGray
                        ),
                        modifier = Modifier
                            .width(130.dp)
                            .height(55.dp)
                            .drawBehind {
                                if (!isTimerSelected) {
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
                            text = "To-do",
                            fontSize = 20.sp
                        )
                    }
                }

                HorizontalDivider(color = Color.LightGray)

                AnimatedContent(
                    targetState = isTimerSelected,
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
                    label = "Tab Animation"
                ) { showTimer ->
                    if (showTimer) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(studyItems) { item ->
                                StudyTimerItem(
                                    item = item,
                                    onToggle = {
//                                        studyItems = studyItems.map {
//                                            if (it.title == item.title)
//                                                it.copy(isRunning = !it.isRunning)
//                                            else
//                                                it
//                                        }
                                        timerViewModel.toggleTimer(item.title)
                                    }
                                )
                            }
                        }
                    } else {
                        HomeTodoScreen(
                            studyItems = studyItems,
                            onUpdateItems = { updated ->
                                studyItems = updated
                            }
                        )
                    }
                }
            }
        }
        FloatingButton(
            bottomNavPadding = bottomPaddingValue
        )
    }
}