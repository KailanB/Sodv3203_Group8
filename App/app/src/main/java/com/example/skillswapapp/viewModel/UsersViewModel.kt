package com.example.skillswapapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.skillswapapp.dummyData.DataSource
import com.example.skillswapapp.state.UsersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsersViewModel (
    // add repository here later
): ViewModel() {

    private val _usersUiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val usersUiState: StateFlow<UsersUiState> = _usersUiState

    fun getAllUsers() {
        _usersUiState.value = UsersUiState.Loading

        try {
            // this is later where
            // val usersList = userRepository.getAllUsers()

            // for now loading a list of dummy data to use
            val usersList = DataSource().loadUsers()
            _usersUiState.value = UsersUiState.Success(usersList)
        } catch (exception: Exception) {
            _usersUiState.value = UsersUiState.Error
        }
    }


}