package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.LocationDao
import com.example.skillswapapp.data.entities.Location
import com.example.skillswapapp.data.repository.iRepositories.LocationRepository
import kotlinx.coroutines.flow.Flow

data class OfflineLocationRepository(
    private val locationDao: LocationDao
): LocationRepository {

    override suspend fun insertLocation(location: Location) = locationDao.insert(location)
    override suspend fun updateLocation(location: Location) = locationDao.update(location)
    override suspend fun deleteLocation(location: Location) = locationDao.delete(location)

    override fun getUserLocationStream(id: Int): Flow<Location?> = locationDao.getUserLocation(id)

}