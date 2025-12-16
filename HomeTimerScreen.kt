package com.example.snowtimerapp.ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.snowtimerapp.ui.components.BottomNavBar
import com.example.snowtimerapp.ui.components.FloatingButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeTimerScreen(
    navController: NavHostController,
    uid: String,
    timerViewModel: TimerViewModel
) {
    // ✅ 내 코드 그대로: uid 기준으로 Firestore에서 과목 로딩
    LaunchedEffect(uid) {
        timerViewModel.setUser(uid)
    }

    // ✅ ViewModel 상태는 내 TimerViewModel 버전 그대로 사용
    val studyItems = timerViewModel.studyItems
    val totalSeconds = timerViewModel.totalSeconds

    var isTimerSelected by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"
    var bottomPaddingValue by remember { mutableStateOf(0.dp) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var isOptionSelected by remember { mutableStateOf(true) }
    var isPomodoroMode by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = Color.Black,   // 🔵 배경색
                        contentColor = Color.White,           // 🔹 글자색
                        shape = RoundedCornerShape(12.dp)     // 둥근 모서리
                    )
                }
            },

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
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(40.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = Color(0xFFEDEDED),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(20.dp.toPx())
                            )
                        }
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(3.dp)
                                .drawBehind {
                                    if (isOptionSelected) {
                                        drawRoundRect(
                                            color = Color.Black,
                                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(18.dp.toPx())
                                        )
                                    }
                                }
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    isOptionSelected = true
                                    isPomodoroMode = false
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = "스톱워치",
                                tint = if (isOptionSelected) Color.White else Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(3.dp)
                                .drawBehind {
                                    if (!isOptionSelected) {
                                        drawRoundRect(
                                            color = Color.Black,
                                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(18.dp.toPx())
                                        )
                                    }
                                }
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    isOptionSelected = false
                                    isPomodoroMode = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.HourglassEmpty,
                                contentDescription = "뽀모도로",
                                tint = if (!isOptionSelected) Color.White else Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                if (isPomodoroMode) {
                    PomodoroTimerScreen(
                        onBack = {
                            isPomodoroMode = false
                            isOptionSelected = true
                        }
                    )
                } else {
                    // ---------------- 총 공부 시간 (친구/네 코드 거의 동일) ----------------
                    Column(
                        modifier = Modifier.height(130.dp),
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
                }
                HorizontalDivider(color = Color.LightGray)

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ---------------- 상단 탭 (타이머 / To-do) ----------------
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

                    // ---------------- 탭별 본문 ----------------
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
                            // ✅ 타이머 탭 부분은 "친구 코드 스타일"로: 재생 → 다른 화면으로 이동
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(
                                    items = studyItems,
                                    key = { item -> item.title }
                                ) { item ->
                                    StudyTimerItem(
                                        item = item,
                                        onPlay = {
                                            // 친구 코드처럼 별도 타이머 재생 화면으로 이동
                                            if (isPomodoroMode) {
                                                navController.navigate("pomodoro_play/${item.title}")
                                            } else {
                                                navController.navigate("timer_play/${item.title}")
                                            }
                                        },
                                        textColor = Color.Black,
                                        onDelete = {
                                            // 🔹 이 과목 삭제 (Firestore + 로컬 상태)
                                            timerViewModel.deleteSubject(item.title)
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = "${item.title}이(가) 삭제되었습니다.",
                                                    withDismissAction = false,
                                                    duration = SnackbarDuration.Short)
                                            }
                                        }
                                    )
                                }
                            }
                        } else {
                            // ✅ To-do 탭: 네 코드 그대로 (ViewModel에만 위임)
                            HomeTodoScreen(
                                studyItems = studyItems,
                                onUpdateItems = { updated ->
                                    timerViewModel.updateStudyItems(updated)
                                }
                            )
                        }
                    }
                }
            }
        }

        // ✅ 플로팅 버튼 + bottom padding 은 네 코드 그대로
        FloatingButton(
            bottomNavPadding = bottomPaddingValue,
            navController = navController
        )
    }
}