package com.example.snowtimerapp.ui.screens.groups

import android.R.attr.enabled
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.snowtimerapp.ui.components.BottomNavBar
import com.example.snowtimerapp.ui.components.MyTopAppBar

data class Group(
    val name: String,
    val subject: String,
    val leader: String,
    val isPrivate: Boolean,
    val currentMembers: Int,
    val totalMembers: Int,
    val targetHour: Int,
    val description: String,
    val category: String
)

@Composable
fun GroupListScreen(
    navController: NavHostController
) {
    val categories = listOf("전체", "가입한 그룹")
    val allGroups = listOf(
        Group(
            name = "코틀린 스터디",
            subject = "모바일소프트웨어",
            leader = "눈송1",
            isPrivate = true,
            currentMembers = 2,
            totalMembers = 10,
            targetHour = 2,
            description = "Jetpack Compose를 공부하는 스터디입니다",
            category = "가입한 그룹"
        ),
        Group(
            name = "알고리즘 A+ 목표",
            subject = "알고리즘",
            leader = "눈송2",
            isPrivate = false,
            currentMembers = 5,
            totalMembers = 12,
            targetHour = 4,
            description = "알고리즘 과목에서 A+를 받는 것이 목표인 스터디입니다",
            category = "전체"
        )
    )
    var selectedCategory by remember { mutableStateOf("전체") }
    var menuExpanded by remember { mutableStateOf(false) }
    var showGroupDialog by remember { mutableStateOf(false) }
    var selectedGroupForDialog by remember { mutableStateOf<Group?>(null) }
    val filteredGroups = if (selectedCategory == "전체") {
        allGroups
    } else {
        allGroups.filter { it.category == selectedCategory }
    }

    Scaffold(
        floatingActionButton = {
            FloatingGroupButton(
                onConfirmAdd = {
                    navController.navigate("group_add")
                }
            )
        },
        topBar = {
            Column {
                MyTopAppBar(title = "스터디 그룹")
                HorizontalDivider(color = Color.LightGray)
            }
        },
        bottomBar = {
            Column {
                HorizontalDivider(color = Color.LightGray)
                BottomNavBar(
                    currentRoute = "group",
                    navController = navController
                )
            }
        },
        containerColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedCategory
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "구분",
                    modifier = Modifier.clickable { menuExpanded = true }
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            menuExpanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            filteredGroups.forEach { group ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedGroupForDialog = group
                            showGroupDialog = true
                        }
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 그룹명
                        Text(
                            text = group.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        // 그룹 공개 여부
                        if (group.isPrivate) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "비공개 그룹",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 그룹장
                        Text(
                            text = group.leader,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = "구분점",
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .size(4.dp)
                        )
                        // 과목명
                        Text(
                            text = group.subject,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = "구분점",
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .size(4.dp)
                        )
                        // 인원 (현재/전체)
                        Text(
                            text = "${group.currentMembers}/${group.totalMembers}명",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = "구분점",
                            tint = Color.Black,
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .size(4.dp)
                        )
                        // 목표 시간
                        Text(
                            text = "${group.targetHour}시간",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }

                    Text(
                        text = group.description,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }
                HorizontalDivider(color = Color.LightGray)
            }
        }
    }
    if (showGroupDialog && selectedGroupForDialog != null) {
        val group = selectedGroupForDialog!!
        if (group.isPrivate) {
            PrivateGroupDialog(
                groupName = group.name,
                onDismiss = { showGroupDialog = false },
                onConfirm = { password ->
                    if (password == "1234") { // 임의로 가정
                        showGroupDialog = false
                        navController.navigate("group_detail/${group.name}")
                    } else {
                        println("Password mismatch for ${group.name}")
                    }
                }
            )
        } else {
            JoinConfirmationDialog(
                groupName = group.name,
                subjectName = group.subject,
                onDismiss = { showGroupDialog = false },
                onConfirm = {
                    showGroupDialog = false
                    navController.navigate("group_detail/${group.name}")
                }
            )
        }
    }
}

@Composable
fun JoinConfirmationDialog(
    groupName: String,
    subjectName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(subjectName) },
        text = { Text("${groupName} 그룹에 가입하시겠습니까?")},
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

@Composable
fun PrivateGroupDialog(
    groupName: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var passwordInput by remember { mutableStateOf("") }
    var isPasswordIncorrect by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("비밀번호 입력") },
        text = {
            Column {
                Text(
                    text = "'$groupName' 그룹에 가입하려면 비밀번호를 입력해주세요."
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = passwordInput,
                    onValueChange = {
                        passwordInput = it
                        isPasswordIncorrect = false
                    },
                    label = { Text("비밀번호") },
                    singleLine = true,
                    isError = isPasswordIncorrect
                )
                if (isPasswordIncorrect) {
                    Text(
                        text = "비밀번호가 일치하지 않습니다.",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (passwordInput == "1234") { // 임의로 설정
                        onConfirm(passwordInput)
                    } else {
                        isPasswordIncorrect = true
                    }
                },
                enabled = passwordInput.isNotBlank()
            ) {
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}