package com.example.network.repsitory

import com.example.util.Resource
import com.example.util.model.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    suspend fun insertToDo(todo: ToDo)

    suspend fun deleteToDo(todoId: String)

    fun getAllToDos(): Flow<Resource<List<ToDo>?>>

    suspend fun getToDoById(todoId: String): ToDo?

    fun searchToDoByTitle(query: String): Flow<Resource<List<ToDo>?>>

    fun updateToDo(todoId: String): Flow<Resource<Boolean>>


}