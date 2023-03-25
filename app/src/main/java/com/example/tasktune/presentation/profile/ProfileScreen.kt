package com.example.tasktune.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Home Page")
            },
            navigationIcon = {
                IconButton(onClick = {

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
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {


        }

    }


}