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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snowtimerapp.ui.screens.auth.LoginScreen
import com.example.snowtimerapp.ui.screens.auth.SignUpEmailScreen
import com.example.snowtimerapp.ui.screens.groups.GroupDetailScreen
import com.example.snowtimerapp.ui.screens.groups.GroupListScreen
import com.example.snowtimerapp.ui.screens.home.HomeTimerScreen
import com.example.snowtimerapp.ui.screens.search.SearchScreen
import com.example.snowtimerapp.ui.theme.SnowTimerAppTheme

val wooju = FontFamily(Font(R.font.wooju))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnowTimerAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    containerColor = Color.White
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        NavHost(
                            navController = navController,
                            //startDestination = "login"
                            startDestination = "main"
                        ) {
                            composable("main") {
                                HomeTimerScreen(navController = navController)
                            }
                            composable("login") {
                                LoginScreen(navController = navController)
                            }
                            composable("signup_email") {
                                SignUpEmailScreen(navController = navController)
                            }
                            composable("search") {
                                SearchScreen(navController = navController)
                            }
                            composable("group") {
                                GroupListScreen(navController = navController)
                            }
                            composable("group_detail/{groupName}") { backStackEntry ->
                                val groupName = backStackEntry.arguments?.getString("groupName") ?: ""
                                GroupDetailScreen(navController = navController, groupName = groupName)
                            }

                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SnowTimerAppTheme {
        val navController = rememberNavController()
        LoginScreen(navController = navController)
    }
}