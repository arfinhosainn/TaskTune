package com.example.tasktune.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.network.ToDoApi
import com.example.network.repsitory.ToDoRepository
import com.example.network.repsitory.ToDoRepositoryImpl
import com.example.tasktune.data.remote.AuthRepositoryImpl
import com.example.tasktune.data.remote.authapi.AuthApi
import com.example.tasktune.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesToDoRepository(api: ToDoApi): ToDoRepository {
        return ToDoRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun providesAuthRepository(api: AuthApi, pref: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api, pref)
    }
}