package com.example.skillswapapp.model

data class User(
    val userId: Int,
    val name: String,
    val email: String,
    val password: String,
    val profileIntro: String?,
    val description: String?,
    val preferences: String?,
    val locationId: Int,
    val profilePicture: ByteArray,


    // users have skills and seek skills, updated this model since users
    // will almost always be displayed with their skills
    val userSkills: List<Skill> = emptyList(),
    val userSeeksSkills: List<Skill> = emptyList()
)
