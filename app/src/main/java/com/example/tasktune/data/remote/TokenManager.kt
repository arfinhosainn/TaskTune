package com.example.tasktune.data.remote

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)


    fun getToken(): String? {
        return prefs.getString("jwt", null)
    }

}