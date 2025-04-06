package com.example.skillswapapp.data.repository

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.skillswapapp.data.dao.CategoryDao
import com.example.skillswapapp.data.dao.UserDao
import com.example.skillswapapp.data.entities.Category
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.entities.relations.UserWithoutSecureInfo
import kotlinx.coroutines.flow.Flow

data class OfflineUserRepository(
    private val userDao: UserDao
) : UserRepository {
    override fun getAllUsersStream(): Flow<List<UserWithoutSecureInfo>> = userDao.getAllUsers()
    override fun getUserStream(id: Int): Flow<UserWithoutSecureInfo?> = userDao.getUser(id)
    override fun getAllUsersBySkillIdStream(id: Int): Flow<List<UserWithoutSecureInfo>> = userDao.getAllUsersBySkillId(id)
    override fun getAllUsersByLocationIdStream(id: Int): Flow<List<UserWithoutSecureInfo>> = userDao.getAllUsersByLocationId(id)

    override suspend fun insertUser(user: User) = userDao.insert(user)
    override suspend fun deleteUser(user: User) = userDao.delete(user)
    override suspend fun updateUser(user: User) = userDao.update(user)
}

