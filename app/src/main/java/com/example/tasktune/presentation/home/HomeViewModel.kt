package com.example.tasktune.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktune.domain.repositories.ToDoRepository
import com.example.tasktune.presentation.home.components.HomeState
import com.example.tasktune.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()


    init {
        getAllToDos()
    }

    private fun getAllToDos() {
        viewModelScope.launch {
            repository.getAllToDos().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _homeState.value = HomeState(error = result.message.toString())
                    }
                    is Resource.Loading -> {
                        _homeState.value = HomeState(loading = true)
                    }
                    is Resource.Success -> {
                        _homeState.value = HomeState(data = result.data ?: emptyList())
                    }
                }
            }
        }
    }
}