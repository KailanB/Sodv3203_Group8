package com.example.skillswapapp.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.User

data class UserWithFriends(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id", // from User
        entityColumn = "user_id", // from User
        associateBy = Junction(
            value = Friendship::class,
            parentColumn = "user_id",  // from Friendship
            entityColumn = "friend_id" // from Friendship
        )
    )
    val friends: List<User>
)
