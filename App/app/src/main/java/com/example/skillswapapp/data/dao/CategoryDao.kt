package com.example.skillswapapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.skillswapapp.data.entities.Category
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category)

    @Insert
    suspend fun insertCategories(categories: List<Category>)

    @Query(
        "SELECT * FROM category"
    )
    fun getAllCategory(): Flow<List<Category>>

    @Query(
        "SELECT * FROM category WHERE category_id = :id"
    )
    fun getCategory(id:Int): Flow<Category>
}