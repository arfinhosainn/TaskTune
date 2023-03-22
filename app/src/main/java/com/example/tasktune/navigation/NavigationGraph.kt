package com.example.tasktune.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tasktune.presentation.auth.SignInScreen
import com.example.tasktune.presentation.auth.SignUpScreen
import com.example.tasktune.presentation.home.HomeScreen

@Composable
fun NavigationGraph(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = Screens.SignUpScreen.route) {
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navHostController)

        }
        composable(route = Screens.HomeScreen.route) {
            HomeScreen()
        }
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController = navHostController)
        }
    }

}