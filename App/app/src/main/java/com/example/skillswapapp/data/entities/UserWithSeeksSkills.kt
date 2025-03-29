package com.example.skillswapapp.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.skillswapapp.model.Skill

data class UserWithSeeksSkills(
    @Embedded val user : User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "skill_id",
        associateBy = Junction(UserSeeksSkills::class)
    )
    val skills: List<Skill>
)
