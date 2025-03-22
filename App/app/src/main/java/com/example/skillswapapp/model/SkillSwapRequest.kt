package com.example.skillswapapp.model

data class SkillSwapRequest(
    val requestId: Int,
    val requestStatus: String, // 'pending', 'accepted', or 'rejected'
    val userIdTo: Int,
    val userIdFrom: Int,
    val appointmentTime: String,
    val details: String?,
    val status: String
)
