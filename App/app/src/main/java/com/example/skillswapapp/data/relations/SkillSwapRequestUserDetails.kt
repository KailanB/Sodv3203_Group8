package com.example.skillswapapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.skillswapapp.data.entities.SkillSwapRequest
import com.example.skillswapapp.data.entities.User

data class SkillSwapRequestUserDetails(
    @Embedded val request: SkillSwapRequest,
    @Relation(
        parentColumn = "user_id_from",
        entityColumn = "user_id"
    )
    val sender: User
)

