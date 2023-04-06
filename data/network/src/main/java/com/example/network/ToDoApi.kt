package com.example.network

import com.example.util.model.ApiResponse
import com.example.util.model.ToDo
import retrofit2.http.*

interface ToDoApi {

    @POST("todos/create")
    suspend fun insertToDo(@Body toDo: ToDo): ApiResponse

    @DELETE("/todos/{id}/delete")
    suspend fun deleteToDo(
        @Path("id") id: String
    ): ApiResponse

    @GET("/todos")
    suspend fun getAllToDos(): ApiResponse


    @GET("todos/{id}")
    suspend fun getToDoById(
        @Path("id") id: String
    ): ApiResponse

    @GET("todos/search")
    suspend fun searchToDoByTitle(
        @Path("query") query: String
    ): ApiResponse


    @PUT("todos/{id}/update")
    suspend fun updateToDo(
        @Path("id") id: String
    ): ApiResponse

}