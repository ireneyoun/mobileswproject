package com.example.snowtimerapp.ui.screens.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        var statusMessage by remember { mutableStateOf("공부 열심히 하자!") }
        var showStatusDialog by remember { mutableStateOf(false) }

        LazyColumn (
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            item {
                // 프로필 영역
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "눈송이",
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = statusMessage,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "상태메시지 수정",
                        tint = Color.Gray,
                        modifier = Modifier
                            .clickable { showStatusDialog = true }
                            .padding(8.dp)
                    )
                }

                HorizontalDivider(color = Color.LightGray)
                Spacer(modifier = Modifier.height(16.dp))

                // 닉네임
                SettingItem(
                    title = "닉네임",
                    value = "눈송이"
                )

                // 상태메시지
                SettingItem(
                    title = "상태메시지",
                    value = statusMessage,
                    onClick = { showStatusDialog = true }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "다크모드",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = switchOn,
                        onCheckedChange = { switchOn = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color.LightGray)
                Spacer(modifier = Modifier.height(16.dp))

                // 이용약관
                SettingItem(title = "이용약관")

                // 개인정보 처리방침
                SettingItem(title = "개인정보 처리방침")

                // 앱 버전
                SettingItem(
                    title = "앱 버전",
                    value = "800.3.80"
                )

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = Color.LightGray)
                Spacer(modifier = Modifier.height(16.dp))

                // 로그아웃
                SettingItem(title = "로그아웃")

                // 탈퇴하기
                SettingItem(title = "탈퇴하기")
            }
        }

        if (showStatusDialog) {
            StatusMessageDialog(
                initialValue = statusMessage,
                onConfirm = {
                    statusMessage = it
                    showStatusDialog = false
                },
                onDismiss = { showStatusDialog = false }
            )
        }

    }
}

@Composable
private fun SettingItem(
    title: String,
    value: String? = null,
    onClick: (() -> Unit)? = null
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )

            if (value != null) {
                Text(
                    text = value,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun StatusMessageDialog(
    initialValue: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialValue) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("상태메시지 수정") },
        text = {
            androidx.compose.material3.OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                singleLine = true
            )
        },
        confirmButton = {
            Text(
                text = "확인",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onConfirm(text) }
            )
        },
        dismissButton = {
            Text(
                text = "취소",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onDismiss() }
            )
        }
    )
}