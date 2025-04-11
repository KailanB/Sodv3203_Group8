package com.example.skillswapapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSeeksSkillsRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSkillsRepository
import com.example.skillswapapp.model.UiDisplaySkill
import com.example.skillswapapp.model.UiUserDisplay
import com.example.skillswapapp.state.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel (
    private val userRepository: UserRepository,
    private val userSkillsRepository: UserSkillsRepository,
    private val userSeeksSkillsRepository: UserSeeksSkillsRepository
): ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState


    init {
        getAllUsers()
    }

    fun getAllUsers() {
        _homeUiState.value = HomeUiState.Loading
        viewModelScope.launch {
            try {
                val usersFlow = userRepository.getAllUsersStream()

                usersFlow.collect{userList ->
                    val usersWithSkills = userList.map { user ->
                        val skills =
                            userSkillsRepository.getAllUserSkillsByIdStream(user.user_id).first()
                        val seeksSkills =
                            userSeeksSkillsRepository.getAllUserSeeksSkillsByIdStream(user.user_id)
                                .first()

                        val uiSkills = skills.map { it.toUiDisplaySkill() }
                        val uiSkillsSeeking = seeksSkills.map { it.toUiDisplaySkill() }
                            UiUserDisplay(user, uiSkills, uiSkillsSeeking)
                        }
                        _homeUiState.value = HomeUiState.Success(users = usersWithSkills)
                }

            } catch (exception: Exception) {
                Log.d("ERROR123", "getAllCategories: ERROR")
                _homeUiState.value = HomeUiState.Error
            }
        }
    }

    fun UserSkillDetails.toUiDisplaySkill(): UiDisplaySkill {
        return UiDisplaySkill(
            skillId = userSkills.skill_id,
            skillName = skill_name,
            description = userSkills.skill_description
        )
    }
    fun UserSeeksSkillsDetails.toUiDisplaySkill(): UiDisplaySkill {
        return UiDisplaySkill(
            skillId = userSeeksSkills.skill_id,
            skillName = skill_name,
            description = userSeeksSkills.skill_seekers_description
        )
    }
}