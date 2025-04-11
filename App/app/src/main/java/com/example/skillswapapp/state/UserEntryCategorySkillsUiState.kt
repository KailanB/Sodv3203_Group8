package com.example.skillswapapp.state

import com.example.skillswapapp.data.relations.CategoryWithSkills

sealed interface UserEntryCategorySkillsUiState {
    data class Success(
        val categories: List<CategoryWithSkills> = emptyList(),

    ): UserEntryCategorySkillsUiState
    object Error: UserEntryCategorySkillsUiState
    object Loading: UserEntryCategorySkillsUiState
}


