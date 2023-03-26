package com.example.tasktune.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tasktune.presentation.add_edit_todo.AddEditTodoScreen
import com.example.tasktune.presentation.auth.SignInScreen
import com.example.tasktune.presentation.auth.SignUpScreen
import com.example.tasktune.presentation.calendar.CalendarScreen
import com.example.tasktune.presentation.home.HomeScreen
import com.example.tasktune.presentation.home.HomeViewModel
import com.example.tasktune.presentation.message.MessageScreen
import com.example.tasktune.presentation.profile.ProfileScreen

@Composable
fun NavigationGraph(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = Screens.AddEditTodo.route) {
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navHostController)

        }
        composable(route = Screens.Home.route) {

            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeState by homeViewModel.homeState.collectAsState()
            HomeScreen(navHostController)
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
        composable(route = Screens.AddEditTodo.route) {
            AddEditTodoScreen(navController = navHostController)
        }
    }

}