package com.example.skillswapapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation


data class LocationWithUsers(
    @Embedded val location: Location,
    @Relation(
        parentColumn = "location_id",
        entityColumn = "location_id"

    )

    val users: List<User>
)
