package com.example.snowtimerapp.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snowtimerapp.ui.components.MyTopAppBar

@Composable
fun SignUpSchoolScreen() {
    var std by remember { mutableStateOf("") }
    var major by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Column {
                MyTopAppBar(title = "회원가입")
                HorizontalDivider(color = Color.LightGray)
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier.height(50.dp)
                )

                Text(
                    text = "학번과 전공을 입력해주세요",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(
                    modifier = Modifier.height(60.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "학번",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 30.dp)
                    )

                    TextField(
                        value = std,
                        onValueChange = { std = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedIndicatorColor = Color.Black
                        )
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "전공",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 30.dp)
                    )

                    TextField(
                        value = major,
                        onValueChange = { major = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedIndicatorColor = Color.Black
                        )
                    )
                }

                Spacer(
                    modifier = Modifier.height(200.dp)
                )

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF053FA5),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(200.dp)
                ) {
                    Text(
                        text = "확인",
                        fontSize = 20.sp
                    )
                }
            }
        }
    )
}