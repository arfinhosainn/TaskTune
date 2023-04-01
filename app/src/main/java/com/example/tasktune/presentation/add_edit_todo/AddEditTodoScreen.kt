package com.example.tasktune.presentation.add_edit_todo

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun WriteScreen(
    viewModel: AddEditToDoViewModel = hiltViewModel(),
    navController: NavController,
    onClosedPressed: () -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onColorChanged: (Int) -> Unit,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    toDoTextFieldState: ToDoTextFieldState,
    onSavedClick: () -> Unit,
    title: String,
    description: String,
    startTime: LocalTime,
    endTime: LocalTime,
    selectedDate: LocalDate
) {

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddEditToDoViewModel.UiEvent.SaveTodo -> {
                    navController.navigateUp()
                }
                is AddEditToDoViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            WriteTopBar(onClosePressed = onClosedPressed)
        }, content = {
            WriteContent(
                toDoTextFieldState = toDoTextFieldState,
                onColorChanged = onColorChanged,
                onTitleChanged = onTitleChanged,
                onDescriptionChanged = onDescriptionChanged,
                title = title,
                description = description,
                onSavedClick = onSavedClick,
                onEndTimeChanged = onEndTimeChanged,
                onStartTimeChanged = onStartTimeChanged,
                onDateChanged = onDateChanged,
                paddingValues = it,
                startTime = startTime,
                endTime = endTime,
                selectedDate = selectedDate
            )
        }
    )
}