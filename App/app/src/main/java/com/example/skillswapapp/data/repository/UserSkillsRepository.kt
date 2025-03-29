package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.entities.UserSkills
import kotlinx.coroutines.flow.Flow

interface UserSkillsRepository {

    suspend fun insertUserSkills(userSkills: UserSkills)
    suspend fun deleteUserSkills(userSkills: UserSkills)

    fun getAllUserSkillsByIdStream(id: Int): Flow<List<UserSkills>>
}