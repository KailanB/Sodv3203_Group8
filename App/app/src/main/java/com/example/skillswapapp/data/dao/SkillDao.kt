package com.example.skillswapapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.skillswapapp.data.entities.Category
import com.example.skillswapapp.data.entities.Skill
import kotlinx.coroutines.flow.Flow


@Dao
interface SkillDao {

    @Insert
    suspend fun insertSkill(skills: Skill)

    @Insert
    suspend fun insertSkills(skills: List<Skill>)

    @Query(
        "SELECT * FROM skill"
    )
    fun getAllSkill(): Flow<List<Skill>>

    @Query(
        "SELECT * FROM skill s JOIN userSkills us ON s.skill_id = us.skill_id " +
                "WHERE us.user_id = :id"
    )
    fun getAllSkillByUserId(id: Int): Flow<List<Skill>>

    @Query(
        "SELECT * FROM skill s JOIN userSeeksSkills uss ON s.skill_id = uss.skill_id " +
                "WHERE uss.user_id = :id"
    )
    fun getAllSeeksSkillByUserId(id: Int): Flow<List<Skill>>

    @Query(
        "SELECT s.skill_id, s.skill_name, s.skill_description, s.category_id FROM skill s " +
                "WHERE s.category_id = :id"
    )
    fun getAllSkillByCategoryId(id:Int): Flow<List<Skill>>

    @Query(
        "SELECT * FROM skill WHERE skill_id = :id"
    )
    fun getSkillById(id:Int): Flow<Skill>


}