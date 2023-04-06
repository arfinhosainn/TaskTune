package com.example.write

import java.time.LocalDate
import java.time.LocalTime

data class TaskTextFieldState(
    val text: String = "",
    val done: Boolean = false,
    val isHintVisible: Boolean = true,
    val date: LocalDate = LocalDate.now(),
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now(),
    val color: Int? = 0

)