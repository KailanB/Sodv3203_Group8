package com.example.skillswapappimport


import android.util.Log
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SessionViewModel (
    private val userRepository: UserRepository,
    private val userSkillsRepository: UserSkillsRepository,
    private val userSeeksSkillsRepository: UserSeeksSkillsRepository,
    private val locationRepository: LocationRepository
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
