package com.example.snowtimerapp.ui.screens.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.snowtimerapp.ui.screens.groups.FloatingGroupButton

@Composable
fun QuestionListScreen(
    navController: NavHostController,
    subject: String
) {
    Scaffold(
        floatingActionButton = {
            FloatingQuestionButton(
                onConfirmAdd = {
                    navController.navigate("question_add")
                }
            )
        },
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "질문게시판",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = subject,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

        },
        containerColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            val question = questionList[0]

            questionList.forEach { question ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("question_detail/${question.id}/$subject")
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = question.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = question.content,
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = "댓글",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(16.dp)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = question.commentCount.toString(),
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
                HorizontalDivider(color = Color.LightGray)
            }
        }
    }
}