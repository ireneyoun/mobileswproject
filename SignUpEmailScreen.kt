package com.example.snowtimerapp.ui.screens.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.snowtimerapp.ui.components.MyTopAppBar
import com.example.snowtimerapp.ui.theme.SnowTimerAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import kotlinx.coroutines.tasks.await

@Composable
fun SignUpEmailScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? Activity
    val auth = remember { FirebaseAuth.getInstance() }

    var email by remember { mutableStateOf("") }
    var isDomainValid by remember { mutableStateOf(true) }
    var verificationSent by remember { mutableStateOf(false) }
    var isVerified by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("인증 링크 전송") }
    var showVerifiedDialog by remember { mutableStateOf(false) }

    // 전송한 이메일을 저장해두었다가 링크로 복귀 시 사용
    val prefs = remember {
        context.getSharedPreferences("signup_prefs", Context.MODE_PRIVATE)
    }

    // Firebase 이메일 링크 설정 (firebase 기본 도메인 사용 → 콘솔 허용리스트 OK)
    val settings = remember {
        actionCodeSettings {
            url = "https://snowtimerapp.firebaseapp.com" // ← 프로젝트 ID로 교체
            handleCodeInApp = true
            setAndroidPackageName(
                "com.example.snowtimerapp",
                true,
                "30"
            )
        }
    }

    // 이메일 링크 전송
    fun sendEmailLink() {
        val target = email.trim()
        if (target.isEmpty()) return
        // 대학 이메일 도메인 검사
        if (!target.endsWith("@sookmyung.ac.kr")) {
            isDomainValid = false
            return
        }
        isDomainValid = true
        buttonText = "전송 중..."
        auth.sendSignInLinkToEmail(target, settings)
            .addOnSuccessListener {
                prefs.edit().putString("pending_email", target).apply()
                verificationSent = true
                buttonText = "이메일 확인 중..."
                Log.d("EmailLink", "Link sent to $target")
            }
            .addOnFailureListener { e ->
                Log.e("EmailLink", "Link send failed", e)
                buttonText = "전송 실패 - 다시 시도"
            }
    }

    // 앱이 이메일 인증 링크로 열렸는지 확인 → 성공 시 팝업 띄우고 버튼 텍스트 변경
    LaunchedEffect(Unit) {
        val link = activity?.intent?.data?.toString() ?: return@LaunchedEffect
        if (auth.isSignInWithEmailLink(link)) {
            val savedEmail = prefs.getString("pending_email", null)
            if (!savedEmail.isNullOrBlank()) {
                try {
                    auth.signInWithEmailLink(savedEmail, link).await()
                    isVerified = true
                    buttonText = "다음 단계"
                    showVerifiedDialog = true
                    Log.d("EmailLink", "Email verified")
                } catch (e: Exception) {
                    Log.e("EmailLink", "Verify failed", e)
                }
            } else {
                Log.e("EmailLink", "No pending email stored")
            }
        }
    }

    // 팝업(아무 데나 탭하면 닫힘)
    if (showVerifiedDialog) {
        AlertDialog(
            onDismissRequest = { showVerifiedDialog = false },
            title = { Text("이메일 인증 완료") },
            text = { Text("이메일 인증이 완료되었습니다.") },
            confirmButton = {} // 버튼 없이 바깥 탭으로 닫힘
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column {
                MyTopAppBar(title = "회원가입")
                Divider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(50.dp))
                Text("이메일을 입력해 주세요", fontWeight = FontWeight.Bold, fontSize = 19.sp)

                Spacer(Modifier.height(60.dp))

                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isDomainValid = it.endsWith("@sookmyung.ac.kr")
                    },
                    placeholder = {
                        Text("example@sookmyung.ac.kr", fontSize = 19.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                if (!isDomainValid && email.isNotEmpty()) {
                    Text(
                        text = "숙명 이메일(@sookmyung.ac.kr)만 사용 가능합니다.",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }

                Spacer(Modifier.height(200.dp))

                Button(
                    onClick = {
                        when {
                            !verificationSent -> sendEmailLink()
                            isVerified -> {
                                // 팝업은 아무 데나 탭하면 닫힘. 버튼은 다음 단계로 이동
                                navController.currentBackStackEntry?.savedStateHandle?.set("email", email.trim())
                                navController.navigate("signup_student")
                            }
                            // verificationSent && !isVerified 인 상태에는 누를 동작 없음(메일 확인 안내 상태)
                        }
                    },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(180.dp)
                ) {
                    Text(buttonText, fontSize = 18.sp)
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