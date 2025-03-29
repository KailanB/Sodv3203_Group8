package com.example.skillswapapp.data.entities

import androidx.room.Entity

@Entity(tableName = "userSeeksSkills", primaryKeys = ["skill_id", "user_id"])
data class UserSeeksSkills(

    val skill_id: Int,
    val user_id: Int,
    val skill_seekers_description: String?,

    )