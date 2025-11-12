package com.example.snowtimerapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeTodoScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Todo-List 화면"
        )
    }
}