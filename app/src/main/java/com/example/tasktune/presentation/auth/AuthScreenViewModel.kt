package com.example.tasktune.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktune.data.remote.authapi.AuthResult
import com.example.tasktune.domain.repositories.AuthRepository
import com.example.tasktune.presentation.auth.components.AuthState
import com.example.tasktune.presentation.auth.components.AuthUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()


    init {
        authenticate()
    }

    fun onEvent(event: AuthUiEvent) {
        when(event) {
            is AuthUiEvent.SignInUsernameChanged -> {
                state = state.copy(signInUsername = event.value)
            }
            is AuthUiEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
            }
            is AuthUiEvent.SignIn -> {
                signIn()
            }
            is AuthUiEvent.SignUpUsernameChanged -> {
                state = state.copy(signUpUsername = event.value)
            }
            is AuthUiEvent.SignUpPasswordChanged -> {
                state = state.copy(signUpPassword = event.value)
            }
            is AuthUiEvent.SignUp -> {
                signUp()
            }
        }
    }


    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signUp(
                userName = state.signUpUsername,
                password = state.signUpPassword
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signIn(
                userName = state.signInUsername,
                password = state.signInPassword
            )
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }


}