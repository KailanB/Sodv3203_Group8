package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.SkillSwapRequestDao
import com.example.skillswapapp.data.entities.SkillSwapRequest
import com.example.skillswapapp.data.relations.SkillSwapRequestDetails
import com.example.skillswapapp.data.repository.iRepositories.SkillSwapRequestRepository
import kotlinx.coroutines.flow.Flow

class OfflineSkillSwapRequestRepository (
    private val skillSwapRequestDao: SkillSwapRequestDao
) : SkillSwapRequestRepository {

    override suspend fun insertSkillSwapRequest(skillSwapRequest: SkillSwapRequest) {
        skillSwapRequestDao.insert(skillSwapRequest)
    }

    override suspend fun deleteSkillSwapRequest(skillSwapRequest: SkillSwapRequest) {
        skillSwapRequestDao.delete(skillSwapRequest)
    }

    override suspend fun getAllSkillSwapRequestsForUser(id:Int): Flow<List<SkillSwapRequestDetails>> {
        return skillSwapRequestDao.getAllSkillSwapRequestsForUser(id)
    }

    override fun getAllSkillSwapRequestsForUserStream(id: Int): Flow<List<SkillSwapRequest>> {
        return skillSwapRequestDao.getAllSkillSwapRequestsForUserStream(id)
    }
}
