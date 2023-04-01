package com.example.tasktune.presentation.add_edit_todo

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ToDoTextFieldState(
    val text: String = "",
    val done: Boolean = false,
    val isHintVisible: Boolean = true,
    val date: LocalDate = LocalDate.now(),
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now(),
    val color: Int? = 0

)