package com.example.tasktune.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.tasktune.data.remote.AuthRepositoryImpl
import com.example.tasktune.data.remote.HttpInterceptor
import com.example.tasktune.data.remote.authapi.AuthApi
import com.example.tasktune.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {

        return Retrofit.Builder().baseUrl("http://192.168.0.11:8089/")
            .addConverterFactory(MoshiConverterFactory.create())
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: HttpInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }


    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun providesAuthRepository(api: AuthApi, pref: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api, pref)
    }


    @Singleton
    @Provides
    fun providesAuthApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): AuthApi {
        return retrofitBuilder.client(okHttpClient).build().create(AuthApi::class.java)
    }

}