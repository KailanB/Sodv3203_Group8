package com.example.skillswapapp.model

data class Users(
    val userId: Int,
    val name: String,
    val email: String,
    val password: String,
    val profileIntro: String?,
    val description: String?,
    val preferences: String?,
    val locationId: Int,
    val profilePicture: ByteArray
)
