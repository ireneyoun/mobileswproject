package com.example.snowtimerapp.ui.screens.groups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import com.example.snowtimerapp.ui.components.BottomNavBar
import com.example.snowtimerapp.ui.components.MyTopAppBar

data class Group(
    val name: String,
    val enrolled: String,
    val category: String,
    val memberCount: Int
)

@Composable
fun GroupListScreen(
    navController: NavHostController
) {
    val categories = listOf("전체", "가입한 그룹")
    val allGroups = listOf(
        Group("스터디 그룹 A", "가입", "알고리즘", 5),
        Group("스터디 그룹 B", "미가입", "알고리즘", 3),
        Group("스터디 그룹 C", "미가입", "컴퓨터네트워크", 4),
        Group("스터디 그룹 D", "가입", "모바일소프트웨어", 6)
    )
    var selectedCategory by remember { mutableStateOf("전체") }
    var menuExpanded by remember { mutableStateOf(false) }
    val filteredGroups = when (selectedCategory) {
        "전체" -> allGroups
        "가입한 그룹" -> allGroups.filter { it.enrolled == "가입" }
        else -> allGroups
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
                    .padding(vertical = 16.dp)
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
                            navController.navigate("group_detail/${group.name}")
                        }
                        .padding(vertical = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = group.name)
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = group.category, color = Color.Gray)
                            Icon(
                                imageVector = Icons.Default.Circle,
                                contentDescription = "구분점",
                                tint = Color.Gray,
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp)
                                    .size(5.dp)
                            )
                            Text(text = "${group.memberCount}명", color = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    HorizontalDivider(color = Color.LightGray)
                }
            }
        }
    }
}