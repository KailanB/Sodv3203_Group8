package com.example.skillswapapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginResult = MutableStateFlow<User?>(null)
    val loginResult: StateFlow<User?> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = userRepository.loginUser(email, password)
            _loginResult.value = user
        }
    }
}
