package com.example.tasktune.presentation.add_edit_todo

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktune.data.remote.dto.ToDo
import com.example.tasktune.data.remote.dto.TodoException
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

    var todoTitle = mutableStateOf(ToDoTextFieldState())
        private set

    var todoDate = mutableStateOf(ToDoTextFieldState())
        private set

    var todoStartTime = mutableStateOf(ToDoTextFieldState())
        private set

    var todoEndTime = mutableStateOf(ToDoTextFieldState())
        private set

    var todoState = mutableStateOf(ToDoTextFieldState(done = false))
        private set

    var todoContent = mutableStateOf(ToDoTextFieldState())
        private set

    var todoColor = mutableStateOf(ToDo.todoColors.random().toArgb())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTodoId: String? = null

    init {
        savedStateHandle.get<String>("todoId")?.let { todoId ->
            if (todoId.isNotEmpty()) {
                viewModelScope.launch {
                    repository.getToDoById(todoId).also { todo ->
                        currentTodoId = todo?.id
                        todoTitle.value = todoTitle.value.copy(
                            text = todo!!.title,
                            isHintVisible = false
                        )
                        todoTitle.value = todoTitle.value.copy(
                            text = todo.content,
                            isHintVisible = false
                        )
                        todoColor.value = todo.color
                    }
                }
            }
        }
    }


    fun onEvent(event: AddEdiTodoEvent) {
        when (event) {
            is AddEdiTodoEvent.ChangeColor -> {
                todoColor.value = event.color
            }
            is AddEdiTodoEvent.EnteredContent -> {
                todoContent.value = todoContent.value.copy(
                    text = event.value
                )
            }
            is AddEdiTodoEvent.ChangeTodoDate -> {
                todoDate.value = todoDate.value.copy(
                    date = event.value
                )
            }
            is AddEdiTodoEvent.ChangeTodoStartTime -> {
                todoStartTime.value = todoStartTime.value.copy(
                    startTime = event.value
                )
            }
            is AddEdiTodoEvent.ChangeToDoState -> {
                todoState.value = todoState.value.copy(
                    done = event.value
                )

            }
            is AddEdiTodoEvent.ChangeTodoEndTime -> {
                todoEndTime.value = todoEndTime.value.copy(
                    endTime = event.value
                )
            }
            is AddEdiTodoEvent.EnteredTitle -> {
                todoTitle.value = todoTitle.value.copy(
                    text = event.value
                )
            }
            AddEdiTodoEvent.SaveTodo -> {
                viewModelScope.launch {
                    try {
                        repository.insertToDo(
                            ToDo(
                                title = todoTitle.value.text,
                                content = todoContent.value.text,
                                date = todoDate.value.date.toString(),
                                startTime = todoStartTime.value.startTime.toString(),
                                endTime = todoEndTime.value.endTime.toString(),
                                done = todoState.value.done,
                                color = todoColor.value
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTodo)
                    } catch (e: TodoException) {
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

