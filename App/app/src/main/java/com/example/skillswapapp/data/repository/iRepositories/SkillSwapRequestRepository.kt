package com.example.skillswapapp.data.repository.iRepositories

import com.example.skillswapapp.data.entities.SkillSwapRequest
import kotlinx.coroutines.flow.Flow

interface SkillSwapRequestRepository {

    suspend fun insertSkillSwapRequest(skillSwapRequest: SkillSwapRequest)
    suspend fun deleteSkillSwapRequest(skillSwapRequest: SkillSwapRequest)

    fun getAllSkillSwapRequestsForUserStream(id:Int): Flow<List<SkillSwapRequest>>
}