package com.example.skillswapapp.state

import com.example.skillswapapp.data.entities.Skill
import com.example.skillswapapp.data.entities.User

interface ProfileUiState {

    data class Success(
        val user: User,
        val skills: List<Skill>,
        val seeksSkills: List<Skill>
    ): ProfileUiState
    object Error: ProfileUiState
    object Loading: ProfileUiState
}