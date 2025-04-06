package com.example.skillswapapp.data.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "category",
    indices = [Index(value = ["category"], unique = true)]
)
data class Category(
    @PrimaryKey(autoGenerate = true)
    val category_id: Int,
    val category: String,
)
