package com.example.tasktune.presentation.add_edit_todo

import java.time.LocalDate
import java.time.LocalTime

sealed class AddEdiTodoEvent {
    data class EnteredTitle(val value: String) : AddEdiTodoEvent()
    data class EnteredContent(val value: String) : AddEdiTodoEvent()
    data class ChangeColor(val color: Int) : AddEdiTodoEvent()
    data class ChangeTodoDate(val value: LocalDate) : AddEdiTodoEvent()
    data class ChangeTodoStartTime(val value: LocalTime) : AddEdiTodoEvent()
    data class ChangeTodoEndTime(val value: LocalTime) : AddEdiTodoEvent()
    data class ChangeToDoState(val value: Boolean) : AddEdiTodoEvent()
    object SaveTodo : AddEdiTodoEvent()
}