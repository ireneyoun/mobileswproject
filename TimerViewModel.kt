package com.example.snowtimerapp.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class StudyItem(
    val title: String,
    val isRunning: Boolean = false,
    var seconds: Int = 0,
    var todos: MutableList<String> = mutableListOf()
)

class TimerViewModel : ViewModel() {
    var studyItems by mutableStateOf(
        listOf(
            StudyItem("알고리즘"),
            StudyItem("컴퓨터네트워크"),
            StudyItem("데이터베이스셜계와질의"),
            StudyItem("모바일소프트웨어"),
            StudyItem("소프트웨어공학"),
            StudyItem("딥러닝개론")
        )
    )
        private set
    val totalSeconds: Int
        get() = studyItems.sumOf { it.seconds }
    private var timerJob: Job? = null

    init {
        startTimerUpdates()
    }

    private fun startTimerUpdates() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                studyItems = studyItems.map { item ->
                    if (item.isRunning) {
                        item.copy(seconds = item.seconds + 1)
                    } else {
                        item
                    }
                }
            }
        }
    }

    fun toggleTimer(itemTitle: String) {
        val newItems = studyItems.map { item ->
            if (item.title == itemTitle) {
                item.copy(isRunning = !item.isRunning)
            } else {
                item.copy(isRunning = false)
            }
        }
        studyItems = newItems
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}