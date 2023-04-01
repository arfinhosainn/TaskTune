package com.example.tasktune.presentation.add_edit_todo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun WriteContent(
    toDoTextFieldState: ToDoTextFieldState,
    onColorChanged: (Int) -> Unit,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    title: String,
    description: String,
    onSavedClick: () -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    paddingValues: PaddingValues,
    startTime: LocalTime,
    endTime: LocalTime,
    selectedDate: LocalDate
) {

    val calenderState = rememberSheetState()
    val endClockState = rememberSheetState()
    val startClockState = rememberSheetState()

    ClockDialog(
        state = startClockState,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            onStartTimeChanged(
                LocalTime.of(
                    hours,
                    minutes
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
            onEndTimeChanged(
                LocalTime.of(
                    hours,
                    minutes
                )
            )
        },
        config = ClockConfig(
            is24HourFormat = false
        )
    )


    CalendarDialog(state = calenderState,
        selection = CalendarSelection.Date { date ->
            onDateChanged(date)
        })



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
                                    color = if (toDoTextFieldState.color == colorInt) {
                                        Color.Black
                                    } else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    onColorChanged(colorInt)
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
                    value = title,
                    onValueChange = onTitleChanged, placeholder = {
                        Text(
                            text = "Task Name",
                            style = TextStyle(
                                fontFamily = FontPoppins,
                                fontSize = 15.sp,
                                color = Color.Black
                            )
                        )
                    }
                )
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
                        value = DateTimeFormatter.ofPattern("hh:mm a").format(startTime),
                        onValueChange = { startTime ->
                            onStartTimeChanged(LocalTime.parse(startTime))
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
                        modifier = Modifier
                            .weight(4f)
                            .clickable {
                                endClockState.show()
                            }, shape = RoundedCornerShape(0.dp),
                        value = DateTimeFormatter.ofPattern("hh:mm a").format(endTime),
                        onValueChange = { endTime ->
                            onEndTimeChanged(LocalTime.parse(endTime))
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
                    value = DateTimeFormatter.ofPattern("EEEE d, MMMM").format(selectedDate),
                    onValueChange = { date ->
                        onDateChanged(LocalDate.parse(date))
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
                    value = description,
                    onValueChange = onDescriptionChanged, placeholder = {
                        Text(
                            text = "Add Description",
                            style = TextStyle(
                                fontFamily = FontPoppins,
                                fontSize = 15.sp,
                                color = Color.Black
                            )
                        )
                    }
                )
            }

            item {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.BottomCenter), shape = RoundedCornerShape(0.dp),
                    onClick = onSavedClick,
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