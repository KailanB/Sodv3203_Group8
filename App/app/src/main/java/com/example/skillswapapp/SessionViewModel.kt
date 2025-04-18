package com.example.skillswapappimport


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.entities.Location
import com.example.skillswapapp.data.repository.iRepositories.LocationRepository
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSeeksSkillsRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSkillsRepository
import com.example.skillswapapp.model.UiUserProfileDisplay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class SessionViewModel (
    private val userRepository: UserRepository,
    private val userSkillsRepository: UserSkillsRepository,
    private val userSeeksSkillsRepository: UserSeeksSkillsRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UiUserProfileDisplay?>(null)
    val currentUser: StateFlow<UiUserProfileDisplay?> = _currentUser

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun login(email: String, password: String) {

        viewModelScope.launch {
            try {

                val checkUser = userRepository.getUserByEmailStream(email.lowercase()).firstOrNull()
                if (checkUser != null && checkUser.password == password) {

                        val userId = checkUser.user_id
                        val userFlow = userRepository.getUserAllInfoStream(userId)
                        val skillsFlow = userSkillsRepository.getAllUserSkillsByIdStream(userId)
                        val seeksSkillsFlow = userSeeksSkillsRepository.getAllUserSeeksSkillsByIdStream(userId)
                        val locationFlow = locationRepository.getUserLocationStream(userId)
                        combine(userFlow, skillsFlow, seeksSkillsFlow, locationFlow) {user, skills, seeksSkills, location ->
                            UiUserProfileDisplay(user, skills, seeksSkills, location)
                        }.collect { combinedUser ->
                            _currentUser.value = combinedUser
                            _isLoggedIn.value = true
                        }
                    }
                else
                {
                    _currentUser.value = null
                    _isLoggedIn.value = false
                }
            } catch (exception: Exception) {
                Log.d("ERROR123", "log in error")
                _currentUser.value = null
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _isLoggedIn.value = false
    }

//    init {
//        getLoggedInUser()
//    }

    fun getLoggedInUser() {
        viewModelScope.launch {
            try {
                // getting user via dummy value 1 since log in functionality is not implemented
                val userId = 1
                val userFlow = userRepository.getUserAllInfoStream(userId)
                val skillsFlow = userSkillsRepository.getAllUserSkillsByIdStream(userId)
                val seeksSkillsFlow = userSeeksSkillsRepository.getAllUserSeeksSkillsByIdStream(userId)
                val locationFlow = locationRepository.getUserLocationStream(userId)

                combine(userFlow, skillsFlow, seeksSkillsFlow, locationFlow) { user, skills, seeksSkills, location ->
                    UiUserProfileDisplay(user, skills, seeksSkills, location)
                }.collect { combinedUser ->
                    _currentUser.value = combinedUser
                }


            } catch (exception: Exception) {
                Log.d("ERROR123", "get user current logged in user error")
                _currentUser.value = null
            }
        }
    }
}
