package com.example.snowtimerapp.ui.screens.home

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.snowtimerapp.ui.components.BottomNavBar
import com.example.snowtimerapp.ui.theme.Noto

@Composable
fun TimerPlayScreen(
    itemId: String,
    navController: NavHostController,
    timerViewModel: TimerViewModel
) {
    LaunchedEffect(itemId) {
        timerViewModel.startTimer(itemId)
    }

    // ✅ TimerViewModel 현재 버전에 맞게
    val studyItems = timerViewModel.studyItems
    val totalSeconds = timerViewModel.totalSeconds

    var isGroupSelected by remember { mutableStateOf(true) }

    // ✅ 현재 과목 찾기
    val currentItem = studyItems.find { it.title == itemId }


    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                Column {
                    HorizontalDivider(color = Color.LightGray)
                    BottomNavBar(
                        currentRoute = "",
                        navController = navController,
                        onItemClickBeforeNavigate = { _ ->
                            timerViewModel.stopTimer(itemId)
                        }
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
                        .background(Color.Gray),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .height(170.dp) // 높이 수정
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${currentItem?.title ?: ""}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = "스톱워치",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text = "${formatTime(currentItem?.seconds ?: 0)}",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                fontFamily = Noto
                            )

                            IconButton(onClick = {
                                timerViewModel.stopTimer(itemId)
                                navController.popBackStack()
                                },
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PauseCircleFilled,
                                    contentDescription = "정지",
                                    tint = Color.White,
                                    modifier = Modifier.size(35.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "총 공부 시간 ${formatTime(totalSeconds)}",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontFamily = Noto
                        )
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
                                .padding(start = 10.dp, end = 10.dp)
                        ) {
                            items(
                                items = studyItems,
                                key = { item -> item.title }
                            ) { item ->
                                val textColor =
                                    if (item.title == itemId) Color.Black else Color.Gray
                                StudyTimerItem(
                                    item = item,
                                    onPlay = null,
                                    textColor = textColor,
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
                            TimerGroupScreen(timerViewModel) // 수정됨
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TimerPlayScreenHeaderPreview() {
    // 프리뷰용 더미 상태
    var isGroupSelected by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 상단 회색 타이머 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray),
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
                    text = "운영체제",   // 프리뷰용 더미 과목명
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )

                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "01 : 23 : 45",   // 프리뷰용 더미 타이머 값
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontFamily = Noto
                    )

                    IconButton(onClick = { /* 프리뷰용, 비워둠 */ }, modifier = Modifier.padding(top = 4.dp)) {
                        Icon(
                            imageVector = Icons.Filled.PauseCircleFilled,
                            contentDescription = "정지",
                            tint = Color.White,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "총 공부 시간  12 : 34 : 56",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = Noto
                )
            }
        }

        // 🔹 과목별 / 그룹별 탭 버튼 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
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

        // 🔹 예시 과목 1개 (StudyTimerItem 대신 간단한 카드 형태)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "운영체제",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "오늘 공부 시간  00 : 45 : 12",
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = Noto
            )
        }
    }
}


