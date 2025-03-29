package com.example.skillswapapp.data.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    @PrimaryKey(autoGenerate = true)
    val location_id: Int,
    val city: String,
    val province: String,
)


