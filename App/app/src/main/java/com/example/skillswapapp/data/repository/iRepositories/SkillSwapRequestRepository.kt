package com.example.skillswapapp.data.repository.iRepositories

import com.example.skillswapapp.data.entities.SkillSwapRequest
import com.example.skillswapapp.data.relations.SkillSwapRequestDetails
import kotlinx.coroutines.flow.Flow

interface SkillSwapRequestRepository {

    suspend fun insertSkillSwapRequest(skillSwapRequest: SkillSwapRequest)
    suspend fun deleteSkillSwapRequest(skillSwapRequest: SkillSwapRequest)

    fun getAllSkillSwapRequestsForUserStream(id:Int): Flow<List<SkillSwapRequest>>
    suspend fun getAllSkillSwapRequestsForUser(id: Int): Flow<List<SkillSwapRequestDetails>>
}