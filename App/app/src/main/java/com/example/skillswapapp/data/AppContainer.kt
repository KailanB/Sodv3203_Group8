package com.example.skillswapapp.data

import android.content.Context
import com.example.skillswapapp.data.database.DatabaseInitializer
import com.example.skillswapapp.data.database.SkillSwapDatabase
import com.example.skillswapapp.data.repository.iRepositories.CategoryRepository
import com.example.skillswapapp.data.repository.iRepositories.LocationRepository
import com.example.skillswapapp.data.repository.OfflineCategoryRepository
import com.example.skillswapapp.data.repository.OfflineLocationRepository
import com.example.skillswapapp.data.repository.OfflineSkillRepository
import com.example.skillswapapp.data.repository.OfflineUserRepository
import com.example.skillswapapp.data.repository.OfflineUserSkillsRepository
import com.example.skillswapapp.data.repository.OfflineUserSeeksSkillsRepository
import com.example.skillswapapp.data.repository.iRepositories.SkillRepository
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSeeksSkillsRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSkillsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


interface AppContainer {
    val userRepository: UserRepository
    val userSkillRepository: UserSkillsRepository
    val userSeeksSkillsRepository: UserSeeksSkillsRepository
    val categoryRepository: CategoryRepository
    val skillRepository: SkillRepository
    val locationRepository: LocationRepository
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

    override val userSkillRepository: UserSkillsRepository by lazy {
        OfflineUserSkillsRepository(database.userSkillsDao())
    }

    override val userSeeksSkillsRepository: UserSeeksSkillsRepository by lazy {
        OfflineUserSeeksSkillsRepository(database.userSeeksSkillsDao())
    }

    override val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(database.categoryDao())
    }

    override val skillRepository: SkillRepository by lazy {
        OfflineSkillRepository(database.skillDao())
    }

    override val locationRepository: LocationRepository by lazy {
        OfflineLocationRepository(database.locationDao())
    }

}
