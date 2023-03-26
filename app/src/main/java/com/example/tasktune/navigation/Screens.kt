package com.example.tasktune.navigation

sealed class Screens(val route: String) {
    object Home : Screens("home_screen")
    object AddEditTodo : Screens("add_edit_todo_screen")
    object Profile : Screens("profile_screen")
    object Message : Screens("message_screen")
    object Calendar : Screens("calendar_screen")
    object SignUpScreen : Screens("signup_screen")
    object SignInScreen : Screens("signin_screen")
}
