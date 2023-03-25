package com.example.tasktune.presentation.home.components

import com.example.tasktune.data.remote.dto.ToDo

data class HomeState(
    val loading: Boolean = false,
    val error: String = "",
    val data: List<ToDo> = emptyList()
)