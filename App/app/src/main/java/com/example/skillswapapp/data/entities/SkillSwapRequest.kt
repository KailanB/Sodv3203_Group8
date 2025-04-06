package com.example.skillswapapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "skillSwapRequest", foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id_to"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id_from"],
        onDelete = ForeignKey.CASCADE
    )
])
data class SkillSwapRequest(
    @PrimaryKey(autoGenerate = true)
    val request_id: Int,
    val request_status: String,
    val user_id_to: Int,
    val user_id_from: Int,
    val appointment_time: Long,
    val details: String,
)



