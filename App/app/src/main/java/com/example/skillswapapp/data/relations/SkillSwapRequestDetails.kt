package com.example.skillswapapp.data.relations

data class SkillSwapRequestDetails(

    val request_id: Int,
    val request_status: String,
    val user_id_to: Int,
    val user_id_from: Int,
    val appointment_time: Long,
    val details: String,
    val name: String,
    val email: String,

)
