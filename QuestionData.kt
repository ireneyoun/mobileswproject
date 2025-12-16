package com.example.snowtimerapp.ui.screens.more

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class Question(
    val id: Int,
    val title: String,
    val content: String,
    var commentCount: Int,
    val comments: SnapshotStateList<String> = mutableStateListOf()
)

val questionList = mutableStateListOf(
    Question(
        id = 1,
        title = "5번 문제",
        content = "프로그래밍 과제#3 5번 문제 시간 복잡도 질문이요",
        commentCount = 0
    ),
    Question(
        id = 2,
        title = "27일 수업",
        content = "27일 2분반 수업시간에 공지사항 있었나요?",
        commentCount = 0
    )
)