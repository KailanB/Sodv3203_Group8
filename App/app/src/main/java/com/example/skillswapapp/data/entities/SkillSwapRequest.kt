package com.example.skillswapapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "skillSwapRequest")
data class SkillSwapRequest(
    @PrimaryKey(autoGenerate = true)
    val request_id: Int,
    val request_status: String,
    val user_id_to: Int,
    val user_id_from: Int,
    val appointment_time: Long,
    val details: String,
)



