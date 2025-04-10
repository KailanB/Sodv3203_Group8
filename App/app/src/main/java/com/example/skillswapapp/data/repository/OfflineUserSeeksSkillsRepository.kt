package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.UserSeeksSkillsDao

import com.example.skillswapapp.data.entities.UserSeeksSkills
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails
import com.example.skillswapapp.data.repository.iRepositories.UserSeeksSkillsRepository
import kotlinx.coroutines.flow.Flow

class OfflineUserSeeksSkillsRepository(
    val userSeeksSkillsDao: UserSeeksSkillsDao
) : UserSeeksSkillsRepository {

    override suspend fun deleteUserSeeksSkills(userSeeksSkills: UserSeeksSkills) = userSeeksSkillsDao.delete(userSeeksSkills)
    override suspend fun insertUserSeeksSkills(userSeeksSkills: UserSeeksSkills) = userSeeksSkillsDao.insert(userSeeksSkills)

    override fun getAllUserSeeksSkillsByIdStream(id: Int): Flow<List<UserSeeksSkillsDetails>> = userSeeksSkillsDao.getAllUserSeeksSkillsById(id)

}


