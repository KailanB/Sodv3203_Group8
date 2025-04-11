package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.CategoryDao
import com.example.skillswapapp.data.entities.Category
import com.example.skillswapapp.data.relations.CategoryWithSkills
import com.example.skillswapapp.data.repository.iRepositories.CategoryRepository
import kotlinx.coroutines.flow.Flow

data class OfflineCategoryRepository(
    private val categoryDao: CategoryDao
) : CategoryRepository {
        override fun getAllCategoryStream(): Flow<List<Category>> = categoryDao.getAllCategory()
        override fun getCategoryStream(id:Int): Flow<Category?> = categoryDao.getCategory(id)

    override fun getCategoriesWithSkillsStream(): Flow<List<CategoryWithSkills>> = categoryDao.getCategoriesWithSkills()
}