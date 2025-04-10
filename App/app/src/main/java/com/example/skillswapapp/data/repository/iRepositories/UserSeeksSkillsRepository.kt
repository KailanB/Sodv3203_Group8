package com.example.skillswapapp.data.repository.iRepositories

import com.example.skillswapapp.data.entities.UserSeeksSkills
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import kotlinx.coroutines.flow.Flow

interface UserSeeksSkillsRepository {

    suspend fun insertUserSeeksSkills(userSeeksSkills: UserSeeksSkills)
    suspend fun deleteUserSeeksSkills(userSeeksSkills: UserSeeksSkills)

    fun getAllUserSeeksSkillsByIdStream(id:Int): Flow<List<UserSeeksSkillsDetails>>
}