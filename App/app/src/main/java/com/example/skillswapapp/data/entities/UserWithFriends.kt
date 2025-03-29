package com.example.skillswapapp.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithFriends(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "friend_id",
        associateBy = Junction(Friendship::class)
    )
    val friends: List<User>
)
