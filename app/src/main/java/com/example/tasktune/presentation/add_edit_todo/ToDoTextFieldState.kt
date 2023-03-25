package com.example.tasktune.presentation.add_edit_todo

data class ToDoTextFieldState(
    val text: String = "",
    val hint: String = "",
    val done: Boolean = false,
    val isHintVisible: Boolean = true

)