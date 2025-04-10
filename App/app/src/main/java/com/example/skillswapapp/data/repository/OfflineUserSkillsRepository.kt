package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.UserSkillsDao
import com.example.skillswapapp.data.entities.UserSkills
import com.example.skillswapapp.data.relations.UserSkillDetails
import com.example.skillswapapp.data.repository.iRepositories.UserSkillsRepository
import kotlinx.coroutines.flow.Flow

class OfflineUserSkillsRepository(
    val userSkillsDao: UserSkillsDao
) : UserSkillsRepository {
    override suspend fun deleteUserSkills(userSkills: UserSkills) = userSkillsDao.delete(userSkills)
    override suspend fun insertUserSkills(userSkills: UserSkills) = userSkillsDao.insert(userSkills)

    override fun getAllUserSkillsByIdStream(id: Int): Flow<List<UserSkillDetails>> = userSkillsDao.getAllUserSkillsById(id)
}