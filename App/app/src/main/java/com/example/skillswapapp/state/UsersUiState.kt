package com.example.skillswapapp.state

import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo
import com.example.skillswapapp.model.UiUserDisplay



sealed interface UsersUiState {
    data class Success(
        val users: List<UiUserDisplay> = emptyList(),
        val user: UserWithoutSecureInfo? = null

    ): UsersUiState
    object Error: UsersUiState
    object Loading : UsersUiState

}
