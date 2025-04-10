package com.example.skillswapapp.data.repository.iRepositories

import com.example.skillswapapp.data.entities.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategoryStream(): Flow<List<Category>>
    fun getCategoryStream(id:Int): Flow<Category?>

}