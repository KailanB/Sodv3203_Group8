package com.example.skillswapapp.data.relations

import androidx.room.Embedded
import com.example.skillswapapp.data.entities.UserSkills


data class UserSkillDetails(
    @Embedded val userSkills: UserSkills,
    val skill_name: String,

)
