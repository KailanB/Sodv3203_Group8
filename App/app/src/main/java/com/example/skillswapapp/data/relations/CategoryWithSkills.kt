package com.example.skillswapapp.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.skillswapapp.data.entities.Category
import com.example.skillswapapp.data.entities.Skill

data class CategoryWithSkills(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "category_id"

    )
    val skills: List<Skill>
)
