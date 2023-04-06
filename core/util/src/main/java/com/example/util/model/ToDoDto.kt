package com.example.util.model

import com.example.ui.theme.*
import java.util.*
import kotlinx.serialization.Serializable

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