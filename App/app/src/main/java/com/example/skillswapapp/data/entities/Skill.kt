package com.example.skillswapapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "skill")
data class Skill(
    @PrimaryKey(autoGenerate = true)
    val skill_id: Int,
    val skill_name: String,
    val category_id: Int,
    val skill_description: String?,
    // I have no idea what these are even for.
    // Skill tables refers to all available skills a user could select
//    val start_time: Date?,
//    val end_time: Date?,
//    val available: Int

)