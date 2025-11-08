package com.example.snowtimerapp

import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snowtimerapp.ui.theme.SnowTimerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnowTimerAppTheme {
                Scaffold(
                    containerColor = Color.White
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        LoginScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf(value = "") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_1),
                contentDescription = "Logo Image",
                modifier = Modifier
                    .size(100.dp)
            )
            Text(
                text = "눈송이를 품은 타이머",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
            )
        }

        Spacer(modifier = Modifier.height(100.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "아이디",
                    fontSize = 20.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(80.dp)
                )
                TextField(
                    value = id,
                    onValueChange = { id = it },
                    placeholder = { Text(text = "아이디를 입력하세요") },
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "비밀번호",
                    fontSize = 20.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(80.dp)
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text(text = "비밀번호를 입력하세요") },
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        Button(
            onClick = {},
            modifier = Modifier.width(200.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF053FA5),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "로그인",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "회원가입",
            fontSize = 20.sp,
            fontWeight = FontWeight.Thin,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SnowTimerAppTheme {
        LoginScreen()
    }
}