package com.example.skillswapapp.data

import android.content.Context
import com.example.skillswapapp.data.database.DatabaseInitializer
import com.example.skillswapapp.data.database.SkillSwapDatabase
import com.example.skillswapapp.data.repository.CategoryRepository
import com.example.skillswapapp.data.repository.OfflineCategoryRepository
import com.example.skillswapapp.data.repository.OfflineSkillRepository
import com.example.skillswapapp.data.repository.OfflineUserRepository
import com.example.skillswapapp.data.repository.SkillRepository
import com.example.skillswapapp.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


interface AppContainer {
    val categoryRepository: CategoryRepository
    val skillRepository: SkillRepository
    val userRepository: UserRepository
}


class AppDataContainer(private val context: Context) : AppContainer {

    val database = SkillSwapDatabase.getDatabase(context)
    val initializer = DatabaseInitializer(context)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            initializer.populateDatabase(database)
        }

    }

    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(database.userDao())
    }

    override val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(database.categoryDao())
    }

    override val skillRepository: SkillRepository by lazy {
        OfflineSkillRepository(database.skillDao())
    }

}
