package com.example.skillswapapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friendship", primaryKeys = ["user_id", "friend_id"])
data class Friendship(
    val user_id: Int,
    val friend_id: Int,
    val status: String
)

