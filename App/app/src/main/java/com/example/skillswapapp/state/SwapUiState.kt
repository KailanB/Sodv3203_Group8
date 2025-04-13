package com.example.skillswapapp.state

import com.example.skillswapapp.data.relations.SkillSwapRequestDetails

sealed interface SwapUiState {
    data class Success(
        val swapRequests: List<SkillSwapRequestDetails> = emptyList(),
    ): SwapUiState
    data class Error(val message: String) : SwapUiState
    object Loading : SwapUiState
}