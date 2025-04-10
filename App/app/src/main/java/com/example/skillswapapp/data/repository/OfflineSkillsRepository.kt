package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.SkillDao
import com.example.skillswapapp.data.entities.Skill
import com.example.skillswapapp.data.repository.iRepositories.SkillRepository
import kotlinx.coroutines.flow.Flow

data class OfflineSkillRepository(
    private val skillDao: SkillDao
) : SkillRepository {
    override fun getAllSkillStream(): Flow<List<Skill>> = skillDao.getAllSkill()
    override fun getSkillByIdStream(id: Int): Flow<Skill?> = skillDao.getSkillById(id)
    override fun getAllSkillByCategoryIdStream(id: Int): Flow<List<Skill>> = skillDao.getAllSkillByCategoryId(id)
    override fun getAllSkillByUserIdStream(id: Int): Flow<List<Skill>> = skillDao.getAllSkillByUserId(id)
    override fun getAllSeeksSkillByUserIdStream(id: Int): Flow<List<Skill>> = skillDao.getAllSeeksSkillByUserId(id)

}


