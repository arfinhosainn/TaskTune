package com.example.tasktune.data.remote.dto

import com.example.tasktune.ui.theme.*
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ToDo(
    var title: String,
    val content: String,
    val id: String = UUID.randomUUID().toString(),
    val userId: String? = null,
    var done: Boolean,
    var startTime: String,
    var endTime: String,
    var date: String,
    val color: Int
) {
    companion object {
        val todoColors = listOf(DarkBlue, DarkOrange, DarkPaste, DarkPink, DarkYellow)
    }
}

class TodoException(message: String) : Exception(message)