package com.example.network.repsitory

import com.example.network.ToDoApi
import com.example.util.Resource
import com.example.util.model.ToDo
import com.example.util.model.TodoException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val api: ToDoApi
) : ToDoRepository {

    @Throws(TodoException::class)
    override suspend fun insertToDo(todo: ToDo) {
        if (todo.title.isBlank()) {
            throw TodoException("The title of the todo can't be empty")
        }
        try {
            api.insertToDo(todo)
        } catch (e: IOException) {
            // handle the network exception here
            throw TodoException("Failed to insert the todo due to a network error")
        }
    }


    @Throws(TodoException::class)
    override suspend fun deleteToDo(todoId: String) {
        try {
            api.deleteToDo(todoId).todo
        } catch (e: IOException) {
            throw TodoException("Failed to delete todo due to network error")
        }

    }

    override fun getAllToDos(): Flow<Resource<List<ToDo>?>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getAllToDos()
            emit(Resource.Success(response.todos))
        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message.toString()))

        }
    }

    override suspend fun getToDoById(todoId: String): ToDo? {
        val todoResponse = api.getToDoById(todoId)
        return todoResponse.todo
    }

    override fun searchToDoByTitle(query: String): Flow<Resource<List<ToDo>?>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.searchToDoByTitle(query)
            emit(Resource.Success(response.todos))
        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message.toString()))

        }
    }

    override fun updateToDo(todoId: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.updateToDo(todoId)
            emit(Resource.Success(response.success))
        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))

        } catch (e: HttpException) {
            emit(Resource.Error(e.message.toString()))

        }
    }
}