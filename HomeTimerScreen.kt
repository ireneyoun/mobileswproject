package com.example.snowtimerapp.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.navigation.NavHostController
import com.example.snowtimerapp.ui.components.BottomNavBar
import kotlinx.coroutines.delay

data class StudyItem(
    val title: String,
    var isRunning: Boolean = false,
    var seconds: Int = 0
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeTimerScreen(navController: NavHostController) {
    var studyItems by remember {
        mutableStateOf(
            listOf(
                StudyItem("알고리즘"),
                StudyItem("컴퓨터네트워크"),
                StudyItem("데이터베이스셜계와질의"),
                StudyItem("모바일소프트웨어"),
                StudyItem("소프트웨어공학"),
                StudyItem("딥러닝개론")
            )
        )
    }

    val totalSeconds = studyItems.sumOf { it.seconds }
    var isTimerSelected by remember { mutableStateOf(true) }

    LaunchedEffect(studyItems) {
        while (true) {
            delay(1000L)
            studyItems = studyItems.map {
                if (it.isRunning)
                    it.copy(seconds = it.seconds + 1)
                else
                    it
            }
        }
    }

    Scaffold(
        bottomBar = {
            Column {
                HorizontalDivider(color = Color.LightGray)
                BottomNavBar(navController)
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "총 공부 시간",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = formatTime(totalSeconds),
                fontSize = 40.sp,
                fontWeight = FontWeight.Medium
            )

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
                        slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(400)) togetherWith
                                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(400))
                    } else {
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(400)) togetherWith
                                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(400))
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
                                    studyItems = studyItems.map {
                                        if (it.title == item.title)
                                            it.copy(isRunning = !it.isRunning)
                                        else
                                            it
                                    }
                                }
                            )
                        }
                    }
                } else {
                    HomeTodoScreen()
                }
            }
        }
    }
}

@Composable
fun StudyTimerItem(item: StudyItem, onToggle: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onToggle) {
                Icon(
                    imageVector = if (item.isRunning) Icons.Default.PauseCircleFilled else Icons.Default.PlayCircleFilled,
                    contentDescription = "공부 타이머",
                    tint = Color.Black
                )
            }

            Text(
                text = item.title,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatTime(item.seconds)
            )

            IconButton(onClick = { showDialog = true }) {
                Icon(
                    Icons.Default.Start,
                    contentDescription = "스터디그룹으로 이동"
                )
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = "스터디그룹 이동")
                },
                text = {
                    Text(text = "이동하시겠습니까?")
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "확인")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "취소")
                    }
                }
            )
        }
    }
}

fun formatTime(totalSeconds: Int): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format("%02d: %02d : %02d", hours, minutes, seconds)
}