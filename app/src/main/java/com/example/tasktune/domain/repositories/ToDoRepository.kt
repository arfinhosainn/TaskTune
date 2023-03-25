package com.example.tasktune.domain.repositories

import com.example.tasktune.data.remote.dto.ApiResponse
import com.example.tasktune.data.remote.dto.ToDo
import com.example.tasktune.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    suspend fun insertToDo(todo: ToDo)

    suspend fun deleteToDo(todoId: String)

    fun getAllToDos(): Flow<Resource<List<ToDo>?>>

    suspend fun getToDoById(todoId: String): ToDo?

    fun searchToDoByTitle(query: String): Flow<Resource<List<ToDo>?>>

    fun updateToDo(todoId: String): Flow<Resource<Boolean>>


}