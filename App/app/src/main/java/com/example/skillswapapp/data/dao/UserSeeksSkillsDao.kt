package com.example.skillswapapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.skillswapapp.data.entities.UserSeeksSkills
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails
import kotlinx.coroutines.flow.Flow


@Dao
interface UserSeeksSkillsDao {

    @Insert
    suspend fun insertUserSeeks(userSeeksSkillsList: List<UserSeeksSkills>)

    @Insert
    suspend fun insert(userSeeksSkills: UserSeeksSkills)

    @Update
    suspend fun update(userSeeksSkills: UserSeeksSkills)

    @Update
    suspend fun delete(userSeeksSkills: UserSeeksSkills)

    // should return a list of all skills of a user
    @Query(
        "SELECT uss.skill_id, uss.user_id, uss.skill_seekers_description, s.skill_name FROM userSeeksSkills uss JOIN Skill s ON uss.skill_id = s.skill_id " +
                "JOIN user u ON u.user_id = uss.user_id " +
                "WHERE u.user_id = :id"
    )
    fun getAllUserSeeksSkillsById(id:Int): Flow<List<UserSeeksSkillsDetails>>
}