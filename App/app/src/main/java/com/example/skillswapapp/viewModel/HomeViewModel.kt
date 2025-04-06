package com.example.skillswapapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillswapapp.data.repository.CategoryRepository
import com.example.skillswapapp.dummyData.DataSource
import com.example.skillswapapp.state.HomeUiState
import com.example.skillswapapp.state.UsersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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