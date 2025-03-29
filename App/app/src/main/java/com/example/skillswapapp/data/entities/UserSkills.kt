package com.example.skillswapapp.data.entities

import androidx.room.Entity

@Entity(tableName = "userSkills", primaryKeys = ["user_id", "skill_id"])
data class UserSkills(

    val skill_id: Int,
    val user_id: Int,
    val skill_description: String?,

)