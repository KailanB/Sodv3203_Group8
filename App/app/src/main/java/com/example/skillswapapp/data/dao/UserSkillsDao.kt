package com.example.skillswapapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.skillswapapp.data.entities.UserSkills
import com.example.skillswapapp.data.entities.relations.UserSkillDetails
import kotlinx.coroutines.flow.Flow


@Dao
interface UserSkillsDao {

    @Insert
    suspend fun insert(userSkills: UserSkills)

    @Update
    suspend fun update(userSkills: UserSkills)

    @Update
    suspend fun delete(userSkills: UserSkills)

    // should return a list of all skills of a user
    @Query(
        "SELECT s.skill_id, s.skill_name, us.skill_description AS skill_details FROM userSkills us JOIN Skill s ON us.skill_id = s.skill_id " +
                "JOIN user u ON u.user_id = us.user_id " +
                "WHERE u.user_id = :id"
    )
    fun getAllUserSkillsById(id:Int): Flow<List<UserSkillDetails>>

    // probably don't need
//    @Query(
//        "SELECT * FROM userSkills WHERE user_id = :id"
//    )
//    fun getUsers(id:Int): Flow<UserSkills>
}