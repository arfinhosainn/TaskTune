package com.example.tasktune.presentation.auth.components

data class AuthState(
    val isLoading: Boolean = false,
    val signUpUsername: String = "",
    val signUpPassword: String = "",
    val signInUsername: String = "",
    val signInPassword: String = ""
)