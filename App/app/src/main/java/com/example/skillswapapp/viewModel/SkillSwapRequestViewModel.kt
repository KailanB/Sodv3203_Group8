package com.example.skillswapapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.skillswapapp.data.repository.iRepositories.SkillSwapRequestRepository
import com.example.skillswapapp.state.SwapUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class SkillSwapRequestViewModel(
    private val skillrequestRepository: SkillSwapRequestRepository
) : ViewModel() {

    private val _swapUiState = MutableStateFlow<SwapUiState>(SwapUiState.Loading)
    val swapUiState: StateFlow<SwapUiState> = _swapUiState

    fun loadSwapsForUser(userId: Int) {
        viewModelScope.launch {
            skillrequestRepository.getAllSkillSwapRequestsForUser(userId)
                .catch { e ->
                    _swapUiState.value = SwapUiState.Error(e.message ?: "Unknown error")
                }
                .collect { swaps ->
                    _swapUiState.value = SwapUiState.Success(swapRequests = swaps)
                }
        }
    }
}