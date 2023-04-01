package com.example.tasktune.presentation.add_edit_todo

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable

@Composable
fun WriteTopBar(
    onClosePressed: () -> Unit
) {

    TopAppBar(
        title = {
            Text(text = "Create New Task")
        }, actions = {
            IconButton(onClick = { onClosePressed() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            }
        }
    )


}