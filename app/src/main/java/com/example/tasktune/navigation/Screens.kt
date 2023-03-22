package com.example.tasktune.navigation

sealed class Screens(val route: String){
    object HomeScreen: Screens("home_screen")
    object SignUpScreen: Screens("signup_screen")
    object SignInScreen: Screens("signin_screen")
}
