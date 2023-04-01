package com.example.tasktune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.tasktune.navigation.NavigationGraph
import com.example.tasktune.navigation.Screens
import com.example.tasktune.presentation.common.BottomNavItem
import com.example.tasktune.presentation.common.BottomNavigationBar
import com.example.tasktune.ui.theme.DarkBlue
import com.example.tasktune.ui.theme.TaskTuneTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskTuneTheme {

                val scope = rememberCoroutineScope()
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                val bottomSheetScaffoldState =
                    rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)


                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Home Page")
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Menu"
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* Do something */ }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Search"
                                    )
                                }
                            },
                            elevation = 8.dp
                        )
                    },
                    bottomBar = {

                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = Screens.Home.route,
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = "Chat",
                                    route = Screens.Profile.route,
                                    icon = Icons.Default.Notifications
                                ),
                                BottomNavItem(
                                    name = "Settings",
                                    route = Screens.Message.route,
                                    icon = Icons.Default.Settings
                                ),

                                BottomNavItem(
                                    name = "Calendar",
                                    route = Screens.Calendar.route,
                                    icon = Icons.Default.Settings
                                )
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )

                    },
                    floatingActionButton = {
                        Box {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(Screens.Write.route)
                                },
                                backgroundColor = DarkBlue
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add", tint = Color.White
                                )
                            }
                        }
                    },
                ) { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        NavigationGraph(navHostController = navController)

                    }

                }

            }
        }
    }
}