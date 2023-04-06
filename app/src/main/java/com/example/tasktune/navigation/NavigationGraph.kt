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
import com.example.tasktune.presentation.auth.SignInScreen
import com.example.tasktune.presentation.auth.SignUpScreen
import com.example.tasktune.presentation.calendar.CalendarScreen
import com.example.tasktune.presentation.home.HomeScreen
import com.example.tasktune.presentation.home.HomeViewModel
import com.example.tasktune.presentation.message.MessageScreen
import com.example.tasktune.presentation.profile.ProfileScreen
import com.example.write.AddEditTaskEvent
import com.example.write.WriteScreen
import com.example.write.WriteViewModel

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

        val viewModel: WriteViewModel = hiltViewModel()
        val navController = rememberNavController()

        Log.d("startTime", "writeRoute:  ${viewModel.todoStartTime.value.startTime}")
        Log.d("startTime", "writeRoute: ${viewModel.todoTitle.value.text}")


        WriteScreen(
            navController = navController,
            onClosedPressed = onClosedPressed,
            onEndTimeChanged = {
                viewModel.onEvent(
                    AddEditTaskEvent.ChangeTodoEndTime(it)
                )
            },
            onStartTimeChanged = {
                viewModel.onEvent(
                    AddEditTaskEvent.ChangeTodoStartTime(it)
                )
            },
            onDateChanged = {
                viewModel.onEvent(AddEditTaskEvent.ChangeTodoDate(it))
            },
            onColorChanged = {
                viewModel.onEvent(AddEditTaskEvent.ChangeColor(it))
            },
            onTitleChanged = {
                viewModel.onEvent(AddEditTaskEvent.EnteredTitle(it))
            },
            onDescriptionChanged = {
                viewModel.onEvent(AddEditTaskEvent.EnteredContent(it))
            },
            toDoTextFieldState = viewModel.todoColor.value,
            onSavedClick = {
                viewModel.onEvent(AddEditTaskEvent.SaveTodo)
            },
            title = viewModel.todoTitle.value.text,
            description = viewModel.todoContent.value.text,
            startTime = viewModel.todoStartTime.value.startTime,
            endTime = viewModel.todoEndTime.value.endTime,
            selectedDate = viewModel.todoDate.value.date
        )
    }
}
