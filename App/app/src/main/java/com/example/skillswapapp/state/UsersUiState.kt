package com.example.skillswapapp.state

import com.example.skillswapapp.model.User


sealed interface UsersUiState {
    data class Success(
        val users: List<User> = emptyList(),
        val user: User? = null

    ): UsersUiState
    object Error: UsersUiState
    object Loading : UsersUiState

}
