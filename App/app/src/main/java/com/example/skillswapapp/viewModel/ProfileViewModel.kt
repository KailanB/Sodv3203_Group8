package com.example.skillswapapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.entities.UserSeeksSkills
import com.example.skillswapapp.data.entities.UserSkills
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSeeksSkillsRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSkillsRepository
import com.example.skillswapapp.model.UiUserProfileDisplay
import com.example.skillswapapp.model.UiDisplaySkill
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val userSkillsRepository: UserSkillsRepository,
    private val userSeeksSkillsRepository: UserSeeksSkillsRepository

    ) : ViewModel() {


    private val _currentUser = MutableStateFlow<UiUserProfileDisplay?>(null)
    val currentUser: StateFlow<UiUserProfileDisplay?> = _currentUser

    private val _mySkills = MutableStateFlow<List<UiDisplaySkill>>(emptyList())
    val mySkills: StateFlow<List<UiDisplaySkill>> = _mySkills

    private val _skillsSeeking = MutableStateFlow<List<UiDisplaySkill>>(emptyList())
    val skillsSeeking: StateFlow<List<UiDisplaySkill>> = _skillsSeeking

    fun setUser(user: UiUserProfileDisplay) {
        _currentUser.value = user
    }



    fun setMySkills(skills: List<UserSkillDetails>) {
        _mySkills.value = skills.map { it.toUiDisplaySkill() }
    }

    fun setSkillsSeeking(skills: List<UserSeeksSkillsDetails>) {
        _skillsSeeking.value = skills.map { it.toUiDisplaySkill() }
    }






    fun UiDisplaySkill.toUserSkills(userId: Int): UserSkills {
        return UserSkills(
            skill_id = skillId,
            user_id = userId,
            skill_description = description
        )
    }

    fun UiDisplaySkill.toUserSeeksSkills(userId: Int): UserSeeksSkills {
        return UserSeeksSkills(
            skill_id = skillId,
            user_id = userId,
            skill_seekers_description = description
        )
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
