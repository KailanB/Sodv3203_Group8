package com.example.skillswapapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithSkills(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "category_id"

    )
    val skills: List<Skill>
)
