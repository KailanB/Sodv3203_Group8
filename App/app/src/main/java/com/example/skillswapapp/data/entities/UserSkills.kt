package com.example.skillswapapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "userSkills", primaryKeys = ["user_id", "skill_id"], foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Skill::class,
        parentColumns = ["skill_id"],
        childColumns = ["skill_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class UserSkills(

    val skill_id: Int,
    val user_id: Int,
    val skill_description: String?,

)