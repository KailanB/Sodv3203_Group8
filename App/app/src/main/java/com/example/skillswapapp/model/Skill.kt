package com.example.skillswapapp.model

data class Skill(
    val skillId: Int,
    val skillName: String,
    val categoryId: Int,
    val skillDescription: String?,
    val startTime: String?,
    val endTime: String?,
    val available: Boolean
)
