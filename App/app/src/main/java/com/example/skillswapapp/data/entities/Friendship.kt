package com.example.skillswapapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "friendship", primaryKeys = ["user_id", "friend_id"], foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["friend_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Friendship(
    val user_id: Int,
    val friend_id: Int,
    val status: String
)

