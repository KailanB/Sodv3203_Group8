package com.example.skillswapapp.state

import com.example.skillswapapp.model.Users


sealed interface UsersUiState {
    data class Success(
        val users: List<Users> = emptyList()
    ): UsersUiState
    object Error: UsersUiState
    object Loading : UsersUiState

}
