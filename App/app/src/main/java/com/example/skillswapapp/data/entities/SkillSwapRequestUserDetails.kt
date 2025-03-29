package com.example.skillswapapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SkillSwapRequestUserDetails(
    @Embedded val request: SkillSwapRequest,
    @Relation(
        parentColumn = "user_id_from",
        entityColumn = "user_id"
    )
    val sender: User
)

