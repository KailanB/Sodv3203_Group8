package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.entities.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun insertLocation(location: Location)
    suspend fun updateLocation(location: Location)
    suspend fun deleteLocation(location: Location)

    fun getUserLocationStream(id:Int): Flow<Location?>
}