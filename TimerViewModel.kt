package com.example.snowtimerapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class StudyItem(
    val title: String,
    val seconds: Int = 0,
    val lastStartTime: Long = 0L,
    val isRunning: Boolean = false,
    val todos: MutableList<String> = mutableListOf()
)

class TimerViewModel : ViewModel() {
    private val _studyItems = MutableStateFlow<List<StudyItem>>(emptyList())
    val studyItems: StateFlow<List<StudyItem>> get() = _studyItems

    private val _currentRunningTime = MutableStateFlow(0)
    val currentRunningTime: StateFlow<Int> = _currentRunningTime

    private val timerJobs = mutableMapOf<String, Job>()

    init {
        _studyItems.value = (
            listOf(
                StudyItem("알고리즘"),
                StudyItem("컴퓨터네트워크"),
                StudyItem("데이터베이스설계와질의"),
                StudyItem("모바일소프트웨어"),
                StudyItem("소프트웨어공학"),
                StudyItem("딥러닝개론")
            )
        )
        updateTotalRunningTime()
    }

    private fun updateTotalRunningTime() {
        _currentRunningTime.value = _studyItems.value.sumOf { it.seconds }
    }

    fun startTimer(title: String) {
        stopAllTimers()
        val index = _studyItems.value.indexOfFirst { it.title == title }
        if (index == -1 || timerJobs.containsKey(title)) return

        _studyItems.value = _studyItems.value.toMutableList().apply {
            this[index] = this[index].copy(isRunning = true)
        }

        val job = viewModelScope.launch {
            while (true) {
                delay(1000)
                _studyItems.value = _studyItems.value.toMutableList().apply {
                    val current = this[index]
                    this[index] = current.copy(seconds = current.seconds + 1)
                }
                updateTotalRunningTime()
            }
        }
        timerJobs[title] = job
    }


    fun stopTimer(title: String) {
        val index = _studyItems.value.indexOfFirst { it.title == title }
        if (index == -1) return

        timerJobs[title]?.cancel()
        timerJobs.remove(title)

        _studyItems.value = _studyItems.value.toMutableList().apply {
            this[index] = this[index].copy(isRunning = false)
        }

        updateTotalRunningTime()
    }

    fun stopAllTimers() {
        timerJobs.values.forEach { it.cancel() }
        timerJobs.clear()

        _studyItems.value = _studyItems.value.map {
            if (it.isRunning) it.copy(isRunning = false) else it
        }
        updateTotalRunningTime()
    }

    fun updateStudyItems(newItems: List<StudyItem>) {
        _studyItems.value = newItems
    }
}