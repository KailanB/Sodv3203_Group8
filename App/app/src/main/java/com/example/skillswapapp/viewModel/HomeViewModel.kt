package com.example.skillswapapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.repository.iRepositories.CategoryRepository
import com.example.skillswapapp.state.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel (
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        getAllCategories()
    }

    fun getAllCategories() {
        _homeUiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {
                categoryRepository.getAllCategoryStream().map { HomeUiState.Success(it) }
                    .collect { uiState ->
                        _homeUiState.value = uiState
                    }


            } catch (exception: Exception) {
                Log.d("ERROR123", "getAllCategories: ERROR")
                _homeUiState.value = HomeUiState.Error
            }
        }

    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}