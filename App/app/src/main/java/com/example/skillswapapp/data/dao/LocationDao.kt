package com.example.skillswapapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.skillswapapp.data.entities.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert
    suspend fun insert(location: Location)

    @Insert
    suspend fun insertLocations(locations: List<Location>)

    @Update
    suspend fun update(location: Location)

    @Update
    suspend fun delete(location: Location)

    // get a users+ location data
    @Query(
        "SELECT l.location_id, l.city, l.province FROM location l JOIN `user` u ON u.location_id = l.location_id" +
                " WHERE u.user_id = :id"
    )
    fun getUserLocation(id:Int): Flow<Location?>
}