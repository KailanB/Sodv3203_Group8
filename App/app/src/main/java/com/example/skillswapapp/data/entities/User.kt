package com.example.skillswapapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "user", foreignKeys = [
    ForeignKey(
        entity = Location::class,
        parentColumns = ["location_id"],
        childColumns = ["location_id"],
        onDelete = ForeignKey.CASCADE)
])
data class User(
    @PrimaryKey(autoGenerate = true)
    val user_id: Int,
    val name: String,
    val email: String,
    val password: String,
    val profile_intro: String?,
    val description: String?,
    val preferences: String?,
    val location_id: Int,
    val profile_picture: ByteArray?
)