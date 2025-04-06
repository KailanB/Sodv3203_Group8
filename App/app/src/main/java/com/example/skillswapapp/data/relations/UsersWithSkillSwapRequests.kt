package com.example.skillswapapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.skillswapapp.data.entities.SkillSwapRequest
import com.example.skillswapapp.data.entities.User

data class UsersWithSkillSwapRequests(
    @Embedded val user: User,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id_to"

    )
    val requestsReceived: List<SkillSwapRequest>
)


