package com.example.skillswapapp.data.entities.relations

data class UserWithoutSecureInfo(

    val user_id: Int,
    val name: String,
    val email: String,
    val profile_intro: String?,
    val city: String,
    val province: String,
    val profile_picture: ByteArray?
)
