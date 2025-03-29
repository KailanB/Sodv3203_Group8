package com.example.skillswapapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val category_id: Int,
    val category: String,
)
