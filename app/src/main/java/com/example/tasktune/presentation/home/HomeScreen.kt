package com.example.tasktune.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tasktune.presentation.home.components.HomeState
import com.example.tasktune.ui.theme.DarkBlue
import com.example.tasktune.ui.theme.FontPoppins
import com.example.util.model.ToDo


@Composable
fun HomeScreen(navController: NavController, homeState: HomeState) {


    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Task Name",
                        style = TextStyle(
                            fontFamily = FontPoppins,
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "View All",
                        style = TextStyle(
                            fontFamily = FontPoppins,
                            fontSize = 15.sp,
                            color = DarkBlue,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            items(homeState.data) {
                TodoRow(toDo = it, isCompleted = it.done, onCheckedChange = {

                })
            }
        }


    }

}


@Composable
fun TodoRow(
    toDo: ToDo,
    isCompleted: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCompleted,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = DarkBlue,
                    uncheckedColor = DarkBlue
                )
            )
            Text(
                text = toDo.title,
                style = if (isCompleted) {
                    TextStyle(textDecoration = TextDecoration.LineThrough)
                } else {
                    TextStyle.Default
                }
            )
        }
        Icon(imageVector = Icons.Default.Done, contentDescription = "Done")
    }
}

