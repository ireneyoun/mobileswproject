package com.example.snowtimerapp.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController
) {
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf("과목명") }

    Scaffold(
        topBar = {
            Column {
                MyTopAppBar(title = "과목 검색")
                HorizontalDivider(color = Color.LightGray)
            }
        },
        bottomBar = {
            Column {
                HorizontalDivider(color = Color.LightGray)
                BottomNavBar(
                    currentRoute = "search",
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
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = Color(0xFFEDEDED),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = query,
                            onValueChange = { query = it },
                            placeholder = { Text("검색") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "검색",
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .background(Color.Transparent),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "취소",
                    color = Color.Gray,
                    modifier = Modifier.clickable { query = "" }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioLabelItem(
                    label = "과목명",
                    selected = selected == "과목명",
                    onClick = { selected = "과목명" }
                )
                Spacer(modifier = Modifier.width(16.dp))
                RadioLabelItem(
                    label = "교수명",
                    selected = selected == "교수명",
                    onClick = { selected = "교수명" }
                )
                Spacer(modifier = Modifier.width(16.dp))
                RadioLabelItem(
                    label = "과목 코드",
                    selected = selected == "과목 코드",
                    onClick = { selected = "과목 코드" }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (query.isNotBlank())
                Text(
                    text = "$query 검색 결과 (${selected})"
                )
        }
    }
}

@Composable
fun RadioLabelItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(text = label)
    }
}