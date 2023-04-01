package com.example.tasktune.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasktune.presentation.add_edit_todo.AddEdiTodoEvent
import com.example.tasktune.presentation.add_edit_todo.AddEditToDoViewModel
import com.example.tasktune.presentation.add_edit_todo.WriteScreen
import com.example.tasktune.presentation.auth.SignInScreen
import com.example.tasktune.presentation.auth.SignUpScreen
import com.example.tasktune.presentation.calendar.CalendarScreen
import com.example.tasktune.presentation.home.HomeScreen
import com.example.tasktune.presentation.home.HomeViewModel
import com.example.tasktune.presentation.message.MessageScreen
import com.example.tasktune.presentation.profile.ProfileScreen

@Composable
fun NavigationGraph(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = Screens.SignUpScreen.route) {
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navHostController)

        }
        composable(route = Screens.Home.route) {

            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeState by homeViewModel.homeState.collectAsState()
            HomeScreen(navHostController, homeState = homeState)
        }
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController = navHostController)
        }
        composable(route = Screens.Message.route) {
            MessageScreen()
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen()
        }
        composable(route = Screens.Calendar.route) {
            CalendarScreen()
        }
        writeRoute(onClosedPressed = {
            navHostController.navigateUp()
        })
    }
}


fun NavGraphBuilder.writeRoute(
    onClosedPressed: () -> Unit
) {

    composable(route = Screens.Write.route) {

        val viewModel: AddEditToDoViewModel = hiltViewModel()
        val navController = rememberNavController()

        Log.d("startTime", "writeRoute:  ${viewModel.todoStartTime.value.startTime}")
        Log.d("startTime", "writeRoute: ${viewModel.todoTitle.value.text}")


        WriteScreen(
            navController = navController,
            onClosedPressed = onClosedPressed,
            onEndTimeChanged = {
                viewModel.onEvent(
                    AddEdiTodoEvent.ChangeTodoEndTime(it)
                )
            },
            onStartTimeChanged = {
                viewModel.onEvent(
                    AddEdiTodoEvent.ChangeTodoStartTime(it)
                )
            },
            onDateChanged = {
                viewModel.onEvent(AddEdiTodoEvent.ChangeTodoDate(it))
            },
            onColorChanged = {
                viewModel.onEvent(AddEdiTodoEvent.ChangeColor(it))
            },
            onTitleChanged = {
                viewModel.onEvent(AddEdiTodoEvent.EnteredTitle(it))
            },
            onDescriptionChanged = {
                viewModel.onEvent(AddEdiTodoEvent.EnteredContent(it))
            },
            toDoTextFieldState = viewModel.todoColor.value,
            onSavedClick = {
                viewModel.onEvent(AddEdiTodoEvent.SaveTodo)
            },
            title = viewModel.todoTitle.value.text,
            description = viewModel.todoContent.value.text,
            startTime = viewModel.todoStartTime.value.startTime,
            endTime = viewModel.todoEndTime.value.endTime,
            selectedDate = viewModel.todoDate.value.date
        )
    }
}
