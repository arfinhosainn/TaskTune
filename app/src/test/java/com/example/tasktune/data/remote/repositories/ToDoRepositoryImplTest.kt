package com.example.tasktune.data.remote.repositories

import com.example.tasktune.data.remote.dto.ApiResponse
import com.example.tasktune.data.remote.dto.ToDo
import com.example.tasktune.data.remote.dto.TodoException
import com.example.tasktune.data.remote.todoapi.ToDoApi
import com.example.tasktune.utils.Resource
import junit.framework.Assert
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class ToDoRepositoryImplTest {

    @Mock
    lateinit var toDoApi: ToDoApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    private val repository = ToDoRepositoryImpl(toDoApi)


    @Test
    fun `Insert todo with invalid title should throw Exception`() = runTest {
        val todo = ToDo(
            title = "",
            content = "Lorem Ipsum",
            done = false,
            startTime = "0:0",
            endTime = "0:0",
            color = 12345,
            date = "12-2-2023"
        )
        try {
            repository.insertToDo(todo)
            assert(false) { "Expected TodoException was not thrown" }
        } catch (e: TodoException) {
            assert(e.message == "The title of the todo can't be empty") {
                "Invalid exception message: ${e.message}"
            }
        }
    }


}