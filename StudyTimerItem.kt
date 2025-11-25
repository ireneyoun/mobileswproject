package com.example.snowtimerapp.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircleFilled
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun StudyTimerItem(
    item: StudyItem,
    onToggle: () -> Unit,
    onNavigateGroup: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    var offsetXPx by remember { mutableStateOf(0f) }
    val maxSwipePx = with(density) { 100.dp.toPx() }
    val animatedOffsetDp = with(density) {
        animateFloatAsState(offsetXPx).value.toDp()
    }
    val isButtonVisible = offsetXPx <= -maxSwipePx / 2f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(vertical = 4.dp)
            .height(50.dp)
    ) {
        AnimatedVisibility(
            visible = isButtonVisible,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .zIndex(1f)
                .fillMaxHeight()
                .background(Color.Gray)
                .clickable {
                    showDialog = true
                }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "그룹 이동",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = animatedOffsetDp)
                .zIndex(0f)
                .background(Color.White)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            offsetXPx = if (offsetXPx < -maxSwipePx / 2f) -maxSwipePx else 0f
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            offsetXPx = (offsetXPx + dragAmount)
                                .coerceIn(-maxSwipePx, 0f)
                            change.consume()
                        }
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onToggle,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = if (item.isRunning)
                            Icons.Default.PauseCircleFilled
                        else
                            Icons.Default.PlayCircleFilled,
                        contentDescription = "공부 타이머",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Text(
                    text = item.title,
                    fontSize = 18.sp
                )
            }

            Text(
                text = formatTime(item.seconds),
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 10.dp)
            )
        }

        // 다이얼로그
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("스터디그룹 이동") },
                text = { Text("이동하시겠습니까?") },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        onNavigateGroup()
                        offsetXPx = 0f
                    }) {
                        Text("확인")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("취소")
                    }
                }
            )
        }
    }
}