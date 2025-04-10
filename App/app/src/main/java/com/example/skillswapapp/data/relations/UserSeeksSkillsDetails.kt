package com.example.skillswapapp.data.relations

import androidx.room.Embedded
import com.example.skillswapapp.data.entities.UserSeeksSkills


data class UserSeeksSkillsDetails (
    @Embedded val userSeeksSkills: UserSeeksSkills,
    val skill_name: String,
)
