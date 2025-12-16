package com.example.snowtimerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.snowtimerapp.ui.screens.auth.LoginScreen
import com.example.snowtimerapp.ui.screens.auth.SignUpEmailScreen
import com.example.snowtimerapp.ui.screens.auth.SignUpIdScreen
import com.example.snowtimerapp.ui.screens.auth.SignUpSchoolScreen
import com.example.snowtimerapp.ui.screens.groups.GroupAddScreen
import com.example.snowtimerapp.ui.screens.groups.GroupDetailScreen
import com.example.snowtimerapp.ui.screens.groups.GroupListScreen
import com.example.snowtimerapp.ui.screens.home.HomeTimerScreen
import com.example.snowtimerapp.ui.screens.home.PomodoroPlayScreen
import com.example.snowtimerapp.ui.screens.home.PomodoroTimerScreen
import com.example.snowtimerapp.ui.screens.home.TimerPlayScreen
import com.example.snowtimerapp.ui.screens.home.TimerViewModel
import com.example.snowtimerapp.ui.screens.more.CalendarScreen
import com.example.snowtimerapp.ui.screens.more.QuestionAddScreen
import com.example.snowtimerapp.ui.screens.more.QuestionBoardScreen
import com.example.snowtimerapp.ui.screens.more.QuestionDetailScreen
import com.example.snowtimerapp.ui.screens.more.QuestionListScreen
import com.example.snowtimerapp.ui.screens.more.questionList
import com.example.snowtimerapp.ui.screens.search.SearchScreen
import com.example.snowtimerapp.ui.screens.setting.SettingScreen
import com.example.snowtimerapp.ui.theme.SnowTimerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SnowTimerAppTheme {
                val navController = rememberNavController()
                val timerViewModel: TimerViewModel = viewModel()
                SnowTimerNavHost(navController = navController, timerViewModel = timerViewModel)
            }
        }
    }
}

@Composable
fun SnowTimerNavHost(navController: NavHostController, timerViewModel: TimerViewModel) {
    Scaffold(containerColor = Color.White) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                navController = navController,
                // TODO: 실제 배포 시에는 login 으로 바꾸고, 지금은 테스트용으로 home/noonsong
                startDestination = "home/noon1"
            ) {
                // 로그인 화면
                composable("login") {
                    LoginScreen(navController = navController)
                }

                // 이메일 인증(코드 전송 + 코드 입력)
                composable("signup_email") {
                    SignUpEmailScreen(navController = navController)
                }

                // 학번 + 전공 입력
                composable("signup_school") {
                    SignUpSchoolScreen(navController = navController)
                }

                // 닉네임 + 아이디 + 비밀번호 입력
                composable("signup_id") {
                    SignUpIdScreen(navController = navController)
                }

                // 홈(타이머 + To-do)
                composable(
                    route = "home/{uid}",
                    arguments = listOf(
                        navArgument("uid") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val uid = backStackEntry.arguments?.getString("uid") ?: ""
                    HomeTimerScreen(
                        navController = navController,
                        uid = uid,
                        timerViewModel = timerViewModel
                    )
                }

                // 과목 검색 화면
                composable("search") {
                    SearchScreen(
                        navController = navController,
                        timerViewModel = timerViewModel
                    )
                }

                // 그룹 목록
                composable("group") {
                    GroupListScreen(navController = navController)
                }

                // 그룹 상세
                composable("group_detail/{groupName}") { backStackEntry ->
                    val groupName = backStackEntry.arguments?.getString("groupName") ?: ""
                    GroupDetailScreen(
                        navController = navController,
                        groupName = groupName,
                        timerViewModel = timerViewModel
                    )
                }

                composable("group_add") {
                    GroupAddScreen(navController)
                }
                composable("timer_play/{itemId}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("itemId") ?: ""
                    TimerPlayScreen(id, navController, timerViewModel)
                }
                composable("pomodoro_play/{itemId}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("itemId") ?: ""
                    PomodoroPlayScreen(navController)
                }
                composable("settings") {
                    SettingScreen(navController = navController)
                }
                composable("calendar") {
                    CalendarScreen(navController)
                }
                composable("question_board") {
                    QuestionBoardScreen(navController)
                }
                composable(
                    route = "question_list/{subject}",
                    arguments = listOf(
                        navArgument("subject") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val subject = backStackEntry.arguments?.getString("subject") ?: ""

                    QuestionListScreen(
                        navController = navController,
                        subject = subject
                    )
                }
                composable("question_detail/{id}/{subject}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0

                    QuestionDetailScreen(
                        navController = navController,
                        subject = backStackEntry.arguments?.getString("subject") ?: "",
                        question = questionList.first { it.id == id }
                    )
                }
                composable("question_add") {
                    QuestionAddScreen(navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SnowTimerNavHostPreview() {
    SnowTimerAppTheme {
        val navController = rememberNavController()
        val timerViewModel: TimerViewModel = viewModel()
        SnowTimerNavHost(navController = navController, timerViewModel = timerViewModel)
    }
}