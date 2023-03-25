package com.example.tasktune.presentation.add_edit_todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktune.data.remote.dto.ToDo
import com.example.tasktune.domain.repositories.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AddEditToDoViewModel @Inject constructor(
    private val repository: ToDoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _todoTitle = mutableStateOf(ToDoTextFieldState(hint = "Enter Title..."))
    val todoTitle: State<ToDoTextFieldState> = _todoTitle

    private val _todoDate = mutableStateOf(ToDoTextFieldState(hint = "Enter date "))
    val todoDate: State<ToDoTextFieldState> = _todoDate

    private val _todoStartTime = mutableStateOf(ToDoTextFieldState(hint = "Start"))
    val todoStartTime: State<ToDoTextFieldState> = _todoStartTime

    private val _todoEndTime = mutableStateOf(ToDoTextFieldState(hint = "End"))
    val todoEndTime: State<ToDoTextFieldState> = _todoEndTime

    private val _todoState = mutableStateOf(
        ToDoTextFieldState(
            done = false
        )
    )
    val todoState: State<ToDoTextFieldState> = _todoState


    private val _todoContent = mutableStateOf(
        ToDoTextFieldState(
            hint = "Enter some content"
        )
    )
    val todoContent: State<ToDoTextFieldState> = _todoContent

    private val _todoColor = mutableStateOf(ToDo.noteColors.random().toArgb())
    val todoColor: State<Int> = _todoColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTodoId: String? = null

    init {
        savedStateHandle.get<String>("todoId")?.let { todoId ->
            if (todoId.isNotEmpty()) {
                viewModelScope.launch {
                    repository.getToDoById(todoId).also { todo ->
                        currentTodoId = todo?.id
                        _todoTitle.value = todoTitle.value.copy(
                            text = todo!!.title,
                            isHintVisible = false
                        )
                        _todoTitle.value = _todoTitle.value.copy(
                            text = todo.content,
                            isHintVisible = false
                        )
                        _todoColor.value = todo.color
                    }
                }
            }
        }
    }


    fun onEvent(event: AddEdiTodoEvent) {
        when (event) {
            is AddEdiTodoEvent.ChangeColor -> {
                _todoColor.value = event.color
            }
            is AddEdiTodoEvent.ChangeContentFocus -> {
                _todoContent.value = _todoContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && _todoContent.value.text.isBlank()
                )
            }
            is AddEdiTodoEvent.ChangeTitleFocus -> {
                _todoTitle.value = todoTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && todoTitle.value.text.isBlank()
                )
            }
            is AddEdiTodoEvent.EnteredContent -> {
                _todoContent.value = _todoContent.value.copy(
                    text = event.value
                )
            }
            is AddEdiTodoEvent.ChangeTodoDate -> {
                _todoDate.value = _todoDate.value.copy(
                    text = event.value.toString()
                )
            }
            is AddEdiTodoEvent.ChangeTodoStartTime -> {
                _todoStartTime.value = _todoStartTime.value.copy(
                    text = event.value.toString()
                )
            }
            is AddEdiTodoEvent.ChangeToDoState -> {
                _todoState.value = _todoState.value.copy(
                    done = event.value
                )

            }
            is AddEdiTodoEvent.ChangeTodoEndTime -> {
                _todoEndTime.value = _todoEndTime.value.copy(
                    text = event.value.toString()
                )
            }
            is AddEdiTodoEvent.EnteredTitle -> {
                _todoTitle.value = _todoTitle.value.copy(
                    text = event.value
                )
            }
            AddEdiTodoEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        repository.insertToDo(
                            ToDo(
                                title = todoTitle.value.text,
                                content = todoContent.value.text,
                                date = todoDate.value.text,
                                startTime = todoStartTime.value.text,
                                endTime = todoEndTime.value.text,
                                done = todoState.value.done,
                                color = todoColor.value
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTodo)
                    } catch (e: HttpException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )

                    }
                }

            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveTodo : UiEvent()
    }
}

