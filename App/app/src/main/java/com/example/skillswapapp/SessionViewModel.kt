package com.example.skillswapappimport


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.repository.SkillRepository
import com.example.skillswapapp.data.repository.UserRepository
import com.example.skillswapapp.model.CurrentUser
import com.example.skillswapapp.model.UserWithSkills
import com.example.skillswapapp.state.UsersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SessionViewModel (
    private val userRepository: UserRepository,
    private val skillRepository: SkillRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<CurrentUser?>(null)
    val currentUser: StateFlow<CurrentUser?> = _currentUser

    init {
        getLoggedInUser()
    }

    fun getLoggedInUser() {
        viewModelScope.launch {
            try {
                // getting user via dummy value 1 since log in functionality is not implemented
                userRepository.getUserAllInfoStream(1)
                    .collect { user ->
                        val skills = skillRepository.getAllSkillByUserIdStream(user.user_id).first()
                        val seeksSkills = skillRepository.getAllSeeksSkillByUserIdStream(user.user_id).first()

                        _currentUser.value = CurrentUser(user, skills, seeksSkills)

                    }


            } catch (exception: Exception) {
                Log.d("ERROR123", "get user: ERROR")
                _currentUser.value = null
            }
        }
    }
}
