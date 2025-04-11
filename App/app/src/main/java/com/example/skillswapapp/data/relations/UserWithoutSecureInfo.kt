package com.example.skillswapapp.data.relations

import java.util.prefs.Preferences

data class UserWithoutSecureInfo(

    val user_id: Int,
    val name: String,
    val email: String,
    val profile_intro: String?,
    val description: String?,
    val preferences: String?,
    val city: String,
    val province: String,
    val profile_picture: ByteArray?
)
