package com.example.skillswapapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UsersWithSkillSwapRequests(
    @Embedded val user: User,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id_to"

    )
    val requestsReceived: List<SkillSwapRequest>
)


