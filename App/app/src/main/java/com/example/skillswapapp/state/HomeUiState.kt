package com.example.skillswapapp.state

import com.example.skillswapapp.model.UiUserDisplay



sealed interface HomeUiState {
    data class Success(
        val users: List<UiUserDisplay> = emptyList()

    ): HomeUiState
    object Error: HomeUiState
    object Loading : HomeUiState

}
