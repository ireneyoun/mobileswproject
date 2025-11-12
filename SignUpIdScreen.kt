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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.snowtimerapp.ui.components.MyTopAppBar
import com.example.snowtimerapp.ui.theme.SnowTimerAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

enum class IdCheckState { Idle, Checking, Ok, Taken, Error }

@Composable
fun SignUpIdScreen() {
    var nickname by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 아이디 중복확인 상태

    val scope = rememberCoroutineScope()
    var idCheckState by remember { mutableStateOf(IdCheckState.Idle) }
    var debounce: Job? by remember { mutableStateOf(null) }

    fun checkIdAvailability(username: String) {
        // 빈값이면 아무 것도 안 함
        if (username.isBlank()) {
            idCheckState = IdCheckState.Idle
            return
        }
        idCheckState = IdCheckState.Checking
        debounce?.cancel()
        debounce = scope.launch {
            delay(400) // 디바운스
            try {
                val handle = username.trim().lowercase()
                val exists = FirebaseFirestore.getInstance()
                    .collection("usernames")
                    .document(handle)
                    .get()
                    .await()
                    .exists()
                idCheckState = if (exists) IdCheckState.Taken else IdCheckState.Ok
            } catch (_: Exception) {
                idCheckState = IdCheckState.Error
            }
        }
    }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    suspend fun createAccount(
        email: String,
        password: String,
        nickname: String,
        username: String,
        studentId: String,
        major: String
    ) {
        // 1) Firebase Auth에 사용자 생성
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: return

        // 2) Firestore 트랜잭션으로 아이디 점유 + 프로필 저장(원자적)
        db.runTransaction { tx ->
            val unameRef = db.collection("usernames").document(username)
            val userRef = db.collection("users").document(uid)

            if (tx.get(unameRef).exists()) throw Exception("USERNAME_TAKEN")

            tx.set(unameRef, mapOf("uid" to uid))
            tx.set(userRef, mapOf(
                "uid" to uid,
                "email" to email,
                "nickname" to nickname,
                "username" to username,
                "password" to password,              // 과제용: 실제 서비스면 저장 금지
                "studentId" to studentId,
                "major" to major,
                "createdAt" to FieldValue.serverTimestamp()
            ))
        }.await()
    }

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
                    text = "회원 정보를 입력해 주세요",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                Spacer(
                    modifier = Modifier.height(60.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "닉네임",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.width(85.dp)
                    )

                    TextField(
                        value = nickname,
                        onValueChange = { nickname = it },
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
                        text = "아이디",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.width(85.dp)
                    )

                    TextField(
                        value = id,
                        onValueChange = { new ->
                            val normalized = new.trim().lowercase()
                            id = normalized
                            checkIdAvailability(normalized)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedIndicatorColor = Color.Black
                        ),
                        trailingIcon = {
                            when (idCheckState) {
                                IdCheckState.Checking ->
                                    CircularProgressIndicator(modifier = Modifier, strokeWidth = 2.dp)
                                IdCheckState.Ok ->
                                    Text("가능", color = Color(0xFF2E7D32), fontSize = 12.sp)
                                IdCheckState.Taken ->
                                    Text("사용중", color = Color(0xFFC62828), fontSize = 12.sp)
                                IdCheckState.Error ->
                                    Text("오류", color = Color(0xFFC62828), fontSize = 12.sp)
                                else -> {}
                            }
                        }
                    )
                }
                when (idCheckState) {
                    IdCheckState.Taken ->
                        Text("이미 사용 중인 아이디입니다.", fontSize = 12.sp, color = Color(0xFFC62828))
                    IdCheckState.Ok ->
                        Text("사용 가능한 아이디입니다.", fontSize = 12.sp, color = Color(0xFF2E7D32))
                    IdCheckState.Error ->
                        Text("네트워크 오류가 발생했어요. 다시 시도해 주세요.", fontSize = 12.sp, color = Color(0xFFC62828))
                    else -> {}
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "비밀번호",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.width(85.dp)
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
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

                val canSubmit = nickname.isNotBlank() && password.length >= 6 && idCheckState == IdCheckState.Ok

                Button(
                    onClick = {
                        scope.launch {
                            // ⚠️ 이메일 값은 이전 단계(이메일 인증 화면)에서 넘겨받아야 함.
                            // 지금은 임시로 하드코딩 가능.
                            val email = "student@sookmyung.ac.kr"
                            val studentId = "20231234"  // 임시
                            val major = "컴퓨터과학과"   // 임시

                            createAccount(
                                email = email,
                                password = password,
                                nickname = nickname,
                                username = id,
                                studentId = studentId,
                                major = major
                            )
                        }
                    },
                    enabled = canSubmit,
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
                        text = "계정 생성",
                        fontSize = 20.sp
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun IdPreview() {
    SnowTimerAppTheme {
        val navController = rememberNavController()
        SignUpIdScreen()
    }
}