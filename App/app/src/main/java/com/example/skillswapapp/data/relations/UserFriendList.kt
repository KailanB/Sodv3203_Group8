package com.example.skillswapapp.data.relations

data class UserFriendList(
    val user_id: Int,
    val name: String,
    val email: String,
    val profile_intro: String?,
    val status: String
)
