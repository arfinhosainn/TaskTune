package com.example.tasktune.di

import com.example.network.ToDoApi
import com.example.tasktune.data.remote.HttpInterceptor
import com.example.tasktune.data.remote.authapi.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun providesRetrofit(): Builder {
        return Builder().baseUrl("http://192.168.0.11:8089/")
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: HttpInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }



    @Singleton
    @Provides
    fun providesAuthApi(retrofitBuilder: Builder, okHttpClient: OkHttpClient): AuthApi {
        return retrofitBuilder.client(okHttpClient).build().create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun providesToDoApi(retrofitBuilder: Builder, okHttpClient: OkHttpClient):ToDoApi {
        return retrofitBuilder.client(okHttpClient).build().create(ToDoApi::class.java)
    }
}