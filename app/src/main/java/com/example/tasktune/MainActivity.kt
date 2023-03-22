package com.example.tasktune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.tasktune.navigation.NavigationGraph
import com.example.tasktune.ui.theme.TaskTuneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskTuneTheme {

                val navRemember = rememberNavController()
                NavigationGraph(navHostController = navRemember)

            }
        }
    }
}