package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.entities.UserSeeksSkills
import kotlinx.coroutines.flow.Flow

interface UserSeeksSkillsRepository {

    suspend fun insertUserSeeksSkills(userSeeksSkills: UserSeeksSkills)
    suspend fun deleteUserSeeksSkills(userSeeksSkills: UserSeeksSkills)

    fun getAllUserSeeksSkillsById(id:Int): Flow<List<UserSeeksSkills>>
}