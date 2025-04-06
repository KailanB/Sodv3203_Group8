package com.example.skillswapapp.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.User

data class UserWithFriends(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "friend_id",
        associateBy = Junction(Friendship::class)
    )
    val friends: List<User>
)
