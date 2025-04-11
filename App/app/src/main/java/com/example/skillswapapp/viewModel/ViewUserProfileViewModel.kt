package com.example.skillswapapp.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails
import com.example.skillswapapp.data.repository.iRepositories.LocationRepository
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSeeksSkillsRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSkillsRepository
import com.example.skillswapapp.model.UiDisplaySkill
import com.example.skillswapapp.model.UiViewUserProfileDisplay
import com.example.skillswapapp.state.HomeUiState
import com.example.skillswapapp.state.ViewUserProfileUiState
import com.example.skillswapapp.view.UserDetailsDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ViewUserProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val userSkillsRepository: UserSkillsRepository,
    private val userSeeksSkillsRepository: UserSeeksSkillsRepository
) : ViewModel() {


    private val _viewUserProfileUiState = MutableStateFlow<ViewUserProfileUiState>(ViewUserProfileUiState.Loading)
    val viewUserProfileUiState: StateFlow<ViewUserProfileUiState> = _viewUserProfileUiState



    private val userId: Int = checkNotNull(savedStateHandle[UserDetailsDestination.userIdArg])

    init {
        getUserById(userId)
    }

    fun getUserById(userId: Int) {
        _viewUserProfileUiState.value = ViewUserProfileUiState.Loading
        viewModelScope.launch {

        try {
            val userFlow = userRepository.getUserStream(userId)
            val skillsFlow = userSkillsRepository.getAllUserSkillsByIdStream(userId)
            val seeksSkillsFlow = userSeeksSkillsRepository.getAllUserSeeksSkillsByIdStream(userId)

            combine(userFlow, skillsFlow, seeksSkillsFlow) { user, skills, seeksSkills ->
                UiViewUserProfileDisplay(user, skills, seeksSkills)
            }.collect { combinedUser ->
                _viewUserProfileUiState.value = ViewUserProfileUiState.Success(combinedUser)
            }


        } catch (exception: Exception) {
            Log.d("ERROR123", "view user profile error")
            _viewUserProfileUiState.value = ViewUserProfileUiState.Error
        }

        }
    }

    fun getMySkills(): List<UiDisplaySkill> {
        val state = viewUserProfileUiState.value
        return if (state is ViewUserProfileUiState.Success) {
            state.userWithSkills.skills.map { it.toUiDisplaySkill() }
        } else {
            emptyList()
        }
    }

    fun getSkillsSeeking(): List<UiDisplaySkill> {
        val state = viewUserProfileUiState.value
        return if (state is ViewUserProfileUiState.Success) {
            state.userWithSkills.seeksSkills.map { it.toUiDisplaySkill() }
        } else {
            emptyList()
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
