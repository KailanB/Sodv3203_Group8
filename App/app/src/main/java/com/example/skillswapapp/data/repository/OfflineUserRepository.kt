package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.UserDao
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import kotlinx.coroutines.flow.Flow

data class OfflineUserRepository(
    private val userDao: UserDao
) : UserRepository {
    override fun getAllUsersStream(): Flow<List<UserWithoutSecureInfo>> = userDao.getAllUsers()
    override fun getUserStream(id: Int): Flow<UserWithoutSecureInfo> = userDao.getUser(id)
    override fun getAllUsersBySkillIdStream(id: Int): Flow<List<UserWithoutSecureInfo>> = userDao.getAllUsersBySkillId(id)
    override fun getAllUsersByLocationIdStream(id: Int): Flow<List<UserWithoutSecureInfo>> = userDao.getAllUsersByLocationId(id)
    override fun getUserAllInfoStream(id: Int): Flow<User> = userDao.getUserAllInfo(id)

    override suspend fun insertUser(user: User) = userDao.insert(user)
    override suspend fun deleteUser(user: User) = userDao.delete(user)
    override suspend fun updateUser(user: User) = userDao.update(user)
    override suspend fun loginUser(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }
}

