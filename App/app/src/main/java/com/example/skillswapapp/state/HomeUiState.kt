package com.example.skillswapapp.state

import com.example.skillswapapp.data.entities.Category

sealed interface HomeUiState {

    data class Success(
        val categories: List<Category> = emptyList()
    ): HomeUiState
    object Error: HomeUiState
    object Loading: HomeUiState


}