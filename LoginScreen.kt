package com.example.snowtimerapp.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.snowtimerapp.R

val wooju = FontFamily(Font(R.font.wooju))

@Composable
fun LoginScreen(navController: NavController) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 로고 + 앱 이름
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_1),
                contentDescription = "Logo Image",
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "눈송이를 품은 타이머",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontFamily = wooju
            )
        }

        // 아이디
        OutlinedTextField(
            value = id,
            onValueChange = { id = it },
            placeholder = { Text("아이디를 입력하세요", color = Color.LightGray) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFF053FA5),
                unfocusedIndicatorColor = Color.LightGray,
                cursorColor = Color(0xFF053FA5),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            )
        )

        Spacer(Modifier.height(7.dp))

        // 비밀번호
        PasswordField(
            password = password,
            onPasswordChange = { password = it }
        )

        Spacer(Modifier.height(24.dp))

        // 로그인 버튼
        Button(
            onClick = { /* TODO: 로그인 로직 */ },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF053FA5),
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text("로그인", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(5.dp))

        // 회원가입
        val interaction = remember { MutableInteractionSource() }
        Text(
            text = "회원가입",
            fontSize = 18.sp,
            color = Color.LightGray,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable(
                    interactionSource = interaction,
                    indication = ripple(bounded = false),
                    role = Role.Button
                ) {
                    navController.navigate("signup_email")
                }
        )
    }
}

@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text("비밀번호를 입력하세요", color = Color.LightGray) },
        singleLine = true,
        visualTransformation = if (visible) VisualTransformation.None
        else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            val icon = if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility
            val desc  = if (visible) "비밀번호 숨기기" else "비밀번호 보이기"
            IconButton(onClick = { visible = !visible }) {
                Icon(icon, contentDescription = desc, tint = Color.Gray)
            }
        },
        modifier = modifier
            .fillMaxWidth(0.85f)
            .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color(0xFF053FA5),
            unfocusedIndicatorColor = Color.LightGray,
            cursorColor = Color(0xFF053FA5),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )
}