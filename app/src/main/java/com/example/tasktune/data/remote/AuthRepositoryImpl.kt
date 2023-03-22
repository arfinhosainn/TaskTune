package com.example.tasktune.data.remote

import android.content.SharedPreferences
import com.example.tasktune.data.remote.authapi.AuthApi
import com.example.tasktune.data.remote.authapi.AuthRequest
import com.example.tasktune.data.remote.authapi.AuthResult
import com.example.tasktune.domain.repositories.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val pref: SharedPreferences
):AuthRepository {

    override suspend fun signUp(userName: String, password: String): AuthResult<Unit> {
        return try {
            api.signUp(
                request = AuthRequest(
                    userName = userName,
                    password = password
                )
            )
            signIn(userName, password)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(userName: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = AuthRequest(
                    userName = userName,
                    password = password
                )
            )
            pref.edit().putString("jwt", response.token).apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            api.authenticate()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }
}