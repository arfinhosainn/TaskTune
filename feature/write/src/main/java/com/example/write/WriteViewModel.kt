package com.example.write

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.repsitory.ToDoRepository
import com.example.util.model.ToDo
import com.example.util.model.TodoException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val repository: ToDoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todoTitle = mutableStateOf(TaskTextFieldState())
        private set

    var todoDate = mutableStateOf(TaskTextFieldState())
        private set

    var todoStartTime = mutableStateOf(TaskTextFieldState())
        private set

    var todoEndTime = mutableStateOf(TaskTextFieldState())
        private set

    var todoState = mutableStateOf(TaskTextFieldState(done = false))
        private set

    var todoContent = mutableStateOf(TaskTextFieldState())
        private set

    var todoColor = mutableStateOf(TaskTextFieldState())
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
                        todoColor.value = todoColor.value.copy(
                            color = todo.color
                        )
                    }
                }
            }
        }
    }


    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.ChangeColor -> {
                todoColor.value = todoColor.value.copy(
                    color = event.color
                )
            }
            is AddEditTaskEvent.EnteredContent -> {
                todoContent.value = todoContent.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.ChangeTodoDate -> {
                todoDate.value = todoDate.value.copy(
                    date = event.value
                )
            }
            is AddEditTaskEvent.ChangeTodoStartTime -> {
                todoStartTime.value = todoStartTime.value.copy(
                    startTime = event.value
                )
            }
            is AddEditTaskEvent.ChangeToDoState -> {
                todoState.value = todoState.value.copy(
                    done = event.value
                )

            }
            is AddEditTaskEvent.ChangeTodoEndTime -> {
                todoEndTime.value = todoEndTime.value.copy(
                    endTime = event.value
                )
            }
            is AddEditTaskEvent.EnteredTitle -> {
                todoTitle.value = todoTitle.value.copy(
                    text = event.value
                )
            }
            AddEditTaskEvent.SaveTodo -> {
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
                                color = todoColor.value.color!!
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

