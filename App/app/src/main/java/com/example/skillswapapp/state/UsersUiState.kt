package com.example.skillswapapp.state

import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.entities.relations.UserWithoutSecureInfo


sealed interface UsersUiState {
    data class Success(
        val users: List<UserWithoutSecureInfo> = emptyList(),
        val user: UserWithoutSecureInfo? = null

    ): UsersUiState
    object Error: UsersUiState
    object Loading : UsersUiState

}
