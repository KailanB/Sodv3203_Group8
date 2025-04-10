package com.example.skillswapapp.data.repository.iRepositories

import com.example.skillswapapp.data.entities.UserSkills
import com.example.skillswapapp.data.relations.UserSkillDetails
import kotlinx.coroutines.flow.Flow

interface UserSkillsRepository {

    suspend fun insertUserSkills(userSkills: UserSkills)
    suspend fun deleteUserSkills(userSkills: UserSkills)

    fun getAllUserSkillsByIdStream(id: Int): Flow<List<UserSkillDetails>>
}