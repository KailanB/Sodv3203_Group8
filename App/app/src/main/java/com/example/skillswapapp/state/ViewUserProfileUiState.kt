package com.example.skillswapapp.state

import com.example.skillswapapp.data.entities.Skill
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo
import com.example.skillswapapp.model.UiViewUserProfileDisplay

sealed interface ViewUserProfileUiState {
    data class Success(
        val userWithSkills: UiViewUserProfileDisplay
    ): ViewUserProfileUiState
    object Error: ViewUserProfileUiState
    object Loading: ViewUserProfileUiState
}
