package com.example.tasktune.presentation.add_edit_todo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tasktune.R
import com.example.tasktune.data.remote.dto.ToDo
import com.example.tasktune.ui.theme.DarkBlue
import com.example.tasktune.ui.theme.FontPoppins
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AddEditTodoScreen(
    viewModel: AddEditToDoViewModel = hiltViewModel(),
    navController: NavController
) {

    val scaffoldState = rememberScaffoldState()
    val calenderState = rememberSheetState()
    val endClockState = rememberSheetState()
    val startClockState = rememberSheetState()
    val pickedDate by viewModel.todoDate


    val title = viewModel.todoTitle.value
    val content = viewModel.todoContent.value


    val endTime by remember {
        derivedStateOf {
            viewModel.todoEndTime.value.endTime
        }
    }

    val startTime by remember {
        derivedStateOf {
            viewModel.todoStartTime.value.startTime
        }
    }


    val formattedStartTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm a").format(startTime)
        }
    }


    val formattedEndTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm a").format(endTime)
        }
    }


    ClockDialog(
        state = startClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            viewModel.onEvent(
                AddEdiTodoEvent.ChangeTodoStartTime(
                    LocalTime.of(
                        hours,
                        minutes
                    )
                )
            )
        },
        config = ClockConfig(
            is24HourFormat = false
        )
    )

    ClockDialog(
        state = endClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            viewModel.onEvent(
                AddEdiTodoEvent.ChangeTodoEndTime(
                    LocalTime.of(
                        hours,
                        minutes
                    )
                )
            )
        },
        config = ClockConfig(
            is24HourFormat = false
        )
    )

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern(
                "EEEE d, MMMM"
            ).format(pickedDate.date)
        }
    }

    CalendarDialog(state = calenderState,
        selection = CalendarSelection.Date { date ->
            viewModel.onEvent(AddEdiTodoEvent.ChangeTodoDate(date))
        })


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddEditToDoViewModel.UiEvent.SaveTodo -> {
                    navController.navigateUp()
                }
                is AddEditToDoViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

            }
        }
    }


    Scaffold(scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Create New Task", fontFamily = FontPoppins)
                }, actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues(horizontal = 25.dp)),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                item {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ToDo.todoColors.forEach { color ->
                            val colorInt = color.toArgb()
                            Box(
                                modifier = Modifier
                                    .size(35.dp)
                                    .shadow(0.dp, CircleShape)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        width = 2.dp,
                                        color = if (viewModel.todoColor.value == colorInt) {
                                            Color.Black
                                        } else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        viewModel.onEvent(AddEdiTodoEvent.ChangeColor(colorInt))
                                    }
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Task Name",
                        style = TextStyle(
                            fontFamily = FontPoppins,
                            fontSize = 15.sp
                        )
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(0.dp),
                        value = title.text,
                        onValueChange = {
                            viewModel.onEvent(AddEdiTodoEvent.EnteredTitle(it))
                        }, placeholder = {
                            Text(
                                text = "Task Name",
                                style = TextStyle(
                                    fontFamily = FontPoppins,
                                    fontSize = 15.sp,
                                    color = Color.Black
                                )
                            )
                        })
                }

                item {
                    Text(
                        text = "Set Time",
                        style = TextStyle(
                            fontFamily = FontPoppins,
                            fontSize = 15.sp
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.weight(4f), shape = RoundedCornerShape(0.dp),
                            value = formattedStartTime,
                            onValueChange = { startTime ->
                                viewModel.onEvent(
                                    AddEdiTodoEvent.ChangeTodoStartTime(
                                        LocalTime.parse(startTime)
                                    )
                                )
                            }, leadingIcon = {
                                IconButton(onClick = { startClockState.show() }) {
                                    Icon(
                                        modifier = Modifier.size(25.dp),
                                        imageVector = ImageVector.vectorResource(id = R.drawable.clock),
                                        contentDescription = "", tint = Color.Black,
                                    )

                                }
                            }, readOnly = true
                        )
                        Spacer(modifier = Modifier.width(15.dp))

                        OutlinedTextField(
                            modifier = Modifier.weight(4f).clickable {
                                endClockState.show()
                            }, shape = RoundedCornerShape(0.dp),
                            value = formattedEndTime,
                            onValueChange = { endTime ->
                                viewModel.onEvent(
                                    AddEdiTodoEvent.ChangeTodoEndTime(
                                        LocalTime.parse(endTime)
                                    )
                                )
                            }, leadingIcon = {
                                IconButton(onClick = { endClockState.show() }) {
                                    Icon(
                                        modifier = Modifier.size(25.dp),
                                        imageVector = ImageVector.vectorResource(id = R.drawable.clock),
                                        contentDescription = "", tint = Color.Black
                                    )
                                }
                            }, readOnly = true
                        )
                    }
                }
                item {
                    Text(
                        text = "Set Date",
                        style = TextStyle(
                            fontFamily = FontPoppins,
                            fontSize = 15.sp
                        )
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(0.dp),
                        value = formattedDate,
                        onValueChange = { date ->
                            viewModel.onEvent(AddEdiTodoEvent.ChangeTodoDate(LocalDate.parse(date)))
                        }, readOnly = true, trailingIcon = {
                            IconButton(onClick = { calenderState.show() }) {
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    imageVector = ImageVector.vectorResource(id = R.drawable.calendar),
                                    contentDescription = "Date", tint = Color.Black,
                                )
                            }
                        }
                    )
                }

                item {
                    Text(
                        text = "Description",
                        style = TextStyle(
                            fontFamily = FontPoppins,
                            fontSize = 15.sp
                        )
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp), shape = RoundedCornerShape(0.dp),
                        value = content.text,
                        onValueChange = {
                            viewModel.onEvent(AddEdiTodoEvent.EnteredContent(it))
                        }, placeholder = {
                            Text(
                                text = "Add Description",
                                style = TextStyle(
                                    fontFamily = FontPoppins,
                                    fontSize = 15.sp,
                                    color = Color.Black
                                )
                            )
                        })
                }


                item {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .align(Alignment.BottomCenter), shape = RoundedCornerShape(0.dp),
                        onClick = { viewModel.onEvent(AddEdiTodoEvent.SaveTodo) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = DarkBlue, contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Create task",
                            style = TextStyle(
                                fontFamily = FontPoppins,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}