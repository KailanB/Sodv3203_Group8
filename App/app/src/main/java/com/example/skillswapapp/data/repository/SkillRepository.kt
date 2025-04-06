package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.entities.Skill
import kotlinx.coroutines.flow.Flow

interface SkillRepository {

    fun getAllSkillStream(): Flow<List<Skill>>

    fun getAllSkillByCategoryIdStream(id:Int): Flow<List<Skill>>

    fun getSkillByIdStream(id:Int): Flow<Skill?>

    fun getAllSkillByUserIdStream(id:Int): Flow<List<Skill>>

    fun getAllSeeksSkillByUserIdStream(id:Int): Flow<List<Skill>>
}