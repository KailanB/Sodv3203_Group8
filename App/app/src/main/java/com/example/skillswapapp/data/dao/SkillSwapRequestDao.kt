package com.example.skillswapapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.skillswapapp.data.entities.SkillSwapRequest
import com.example.skillswapapp.data.relations.SkillSwapRequestDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillSwapRequestDao {

    @Insert
    suspend fun insertSkillSwaps(skillSwapRequests: List<SkillSwapRequest>)

    @Insert
    suspend fun insert(skillSwapRequest: SkillSwapRequest)

    @Update
    suspend fun update(skillSwapRequest: SkillSwapRequest)

    @Delete
    suspend fun delete(skillSwapRequest: SkillSwapRequest)

    // should return a list of all skill swap requests for a particular user
    // should also include the requesters name and email (in theory)
    @Query(
        "SELECT ssr.request_id, ssr.request_status AS request_status, ssr.user_id_to, ssr.user_id_from, ssr.appointment_time, ssr.details, " +
                "uRequester.name, uRequester.email " +
                "FROM skillSwapRequest ssr " +
                "JOIN user u ON u.user_id = ssr.user_id_to " +
                "JOIN user uRequester ON uRequester.user_id = ssr.user_id_from " +
                "WHERE u.user_id = :id"
    )
    fun getAllSkillSwapRequestsForUser(id:Int): Flow<List<SkillSwapRequestDetails>>

    @Query("SELECT * FROM skillSwapRequest WHERE user_id_to = :id OR user_id_from = :id")
    fun getAllSkillSwapRequestsForUserStream(id: Int): Flow<List<SkillSwapRequest>>
}