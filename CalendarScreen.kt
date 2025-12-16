package com.example.snowtimerapp.ui.screens.more

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.time.LocalDate
import java.time.YearMonth

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CalendarScreen(
    navController: NavHostController
) {
    val today = LocalDate.now()
    val yearMonth = YearMonth.now()
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7 // 일요일 시작
    val days = buildList {
        repeat(firstDayOfWeek) { add("") }
        for (day in 1..daysInMonth) {
            add(day.toString())
        }
    }
    var selectedDay by remember { mutableStateOf<Int?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var scheduleText by remember { mutableStateOf("") }
    var schedules by remember {
        mutableStateOf<Map<LocalDate, String>>(emptyMap())
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "캘린더",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

        },
        containerColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = "${yearMonth.year}년 ${yearMonth.monthValue}월",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("일", "월", "화", "수", "목", "금", "토").forEach {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        color = if (it == "일") Color.Red else Color.Black,
                        modifier = Modifier.weight(1f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                userScrollEnabled = false
            ) {
                items(days) { day ->
                    val date = if (day.isNotEmpty()) {
                        LocalDate.of(yearMonth.year, yearMonth.month, day.toInt())
                    } else null

                    val schedule = date?.let { schedules[it] }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 90.dp)
                            .then(
                                Modifier.fillMaxHeight()
                            )
                            .padding(4.dp)
                            .clickable(enabled = day.isNotEmpty()) {
                                selectedDay = day.toInt()
                                showDialog = true
                                scheduleText = schedule ?: ""
                            },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        if (day.isNotEmpty()) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                val isToday = day.toInt() == today.dayOfMonth

                                if (isToday) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(Color.Black, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = day,
                                            color = Color.White,
                                            fontSize = 14.sp
                                        )
                                    }
                                } else {
                                    Text(
                                        text = day,
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(top = 6.dp)
                                    )
                                }

                                // ✅ 일정 텍스트 표시
                                if (!schedule.isNullOrBlank()) {
                                    Text(
                                        text = schedule,
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(
                                            top = 4.dp,
                                            start = 2.dp,
                                            end = 2.dp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (showDialog && selectedDay != null) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val date = LocalDate.of(
                                    yearMonth.year,
                                    yearMonth.month,
                                    selectedDay!!
                                )
                                schedules = schedules + (date to scheduleText)
                                showDialog = false
                            }
                        ) {
                            Text("저장")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("취소")
                        }
                    },
                    title = {
                        Text("${yearMonth.monthValue}월 ${selectedDay}일 일정")
                    },
                    text = {
                        OutlinedTextField(
                            value = scheduleText,
                            onValueChange = { scheduleText = it },
                            placeholder = { Text("일정 입력") },
                            singleLine = false
                        )
                    }
                )
            }
        }
    }
}