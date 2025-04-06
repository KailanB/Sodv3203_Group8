package com.example.skillswapapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.skillswapapp.data.entities.Location
import com.example.skillswapapp.data.entities.User


data class LocationWithUsers(
    @Embedded val location: Location,
    @Relation(
        parentColumn = "location_id",
        entityColumn = "location_id"

    )

    val users: List<User>
)
