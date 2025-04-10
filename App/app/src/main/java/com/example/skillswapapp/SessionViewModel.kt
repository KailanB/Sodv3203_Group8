package com.example.skillswapappimport


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSeeksSkillsRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSkillsRepository
import com.example.skillswapapp.model.UiUserProfileDisplay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SessionViewModel (
    private val userRepository: UserRepository,
    private val userSkillsRepository: UserSkillsRepository,
    private val userSeeksSkillsRepository: UserSeeksSkillsRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UiUserProfileDisplay?>(null)
    val currentUser: StateFlow<UiUserProfileDisplay?> = _currentUser

    init {
        getLoggedInUser()
    }

    fun getLoggedInUser() {
        viewModelScope.launch {
            try {
                // getting user via dummy value 1 since log in functionality is not implemented
                userRepository.getUserAllInfoStream(1)
                    .collect { user ->
                        val skills = userSkillsRepository.getAllUserSkillsByIdStream(user.user_id).first()
                        val seeksSkills = userSeeksSkillsRepository.getAllUserSeeksSkillsByIdStream(user.user_id).first()

                        _currentUser.value = UiUserProfileDisplay(user, skills, seeksSkills)

                    }


            } catch (exception: Exception) {
                Log.d("ERROR123", "get user: ERROR")
                _currentUser.value = null
            }
        }
    }
}
