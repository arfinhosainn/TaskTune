package com.example.tasktune.presentation.add_edit_todo

import androidx.compose.ui.focus.FocusState
import java.time.LocalDate
import java.time.LocalDateTime

sealed class AddEdiTodoEvent {
    data class EnteredTitle(val value: String) : AddEdiTodoEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEdiTodoEvent()
    data class EnteredContent(val value: String) : AddEdiTodoEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEdiTodoEvent()
    data class ChangeColor(val color: Int) : AddEdiTodoEvent()
    data class ChangeTodoDate(val value: LocalDate) : AddEdiTodoEvent()
    data class ChangeTodoStartTime(val value: LocalDateTime) : AddEdiTodoEvent()
    data class ChangeTodoEndTime(val value: LocalDateTime) : AddEdiTodoEvent()
    data class ChangeToDoState(val value: Boolean) : AddEdiTodoEvent()
    object SaveNote : AddEdiTodoEvent()
}