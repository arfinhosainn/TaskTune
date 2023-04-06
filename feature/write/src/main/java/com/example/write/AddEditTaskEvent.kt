package com.example.write

import java.time.LocalDate
import java.time.LocalTime

sealed class AddEditTaskEvent {
    data class EnteredTitle(val value: String) : AddEditTaskEvent()
    data class EnteredContent(val value: String) : AddEditTaskEvent()
    data class ChangeColor(val color: Int) : AddEditTaskEvent()
    data class ChangeTodoDate(val value: LocalDate) : AddEditTaskEvent()
    data class ChangeTodoStartTime(val value: LocalTime) : AddEditTaskEvent()
    data class ChangeTodoEndTime(val value: LocalTime) : AddEditTaskEvent()
    data class ChangeToDoState(val value: Boolean) : AddEditTaskEvent()
    object SaveTodo : AddEditTaskEvent()
}