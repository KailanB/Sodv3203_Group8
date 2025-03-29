package com.example.skillswapapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.skillswapapp.data.entities.Category
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {


    @Query(
        "SELECT * FROM category"
    )
    fun getAllCategory(): Flow<List<Category>>

    @Query(
        "SELECT * FROM category WHERE category_id = :id"
    )
    fun getCategory(id:Int): Flow<Category>
}