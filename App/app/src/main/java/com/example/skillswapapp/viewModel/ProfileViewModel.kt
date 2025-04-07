package com.example.skillswapapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.skillswapapp.data.repository.UserRepository
import com.example.skillswapapp.state.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileViewModel(
    private val userRepository: UserRepository,

) : ViewModel() {

    private val _profileUiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState

    init {
        getUser()
    }

    fun getUser() {

    }


}
