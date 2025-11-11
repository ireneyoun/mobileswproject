package com.example.snowtimerapp.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.snowtimerapp.ui.components.MyTopAppBar
import com.example.snowtimerapp.ui.theme.SnowTimerAppTheme

@Composable
fun SignUpEmailScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }

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
            ) {
                Spacer(
                    modifier = Modifier.height(50.dp)
                )

                Text(
                    text = "이메일을 입력해 주세요",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                Spacer(
                    modifier = Modifier.height(60.dp)
                )

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            text = "example.sookmyung.ac.kr",
                            fontSize = 19.sp,
                            color = Color.LightGray,
                            modifier = Modifier.offset(x = (-14.5).dp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedIndicatorColor = Color.Black
                    )
                )

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
                        .width(180.dp)
                ) {
                    Text(
                        text = "확인",
                        fontSize = 18.sp
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun EmailPreview() {
    SnowTimerAppTheme {
        val navController = rememberNavController()
        SignUpEmailScreen(navController = navController)
    }
}