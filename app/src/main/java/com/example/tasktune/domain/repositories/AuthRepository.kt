package com.example.tasktune.domain.repositories

import com.example.tasktune.data.remote.authapi.AuthResult

interface AuthRepository {
    suspend fun signUp(userName: String, password: String): AuthResult<Unit>
    suspend fun signIn(userName: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}