package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.UserDao
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override fun getAllUsersBySkillIdStream(id: Int): Flow<List<UserWithoutSecureInfo>> {
        return userDao.getAllUsersBySkillIdStream(id)
    }

    override fun getAllUsersByLocationIdStream(id: Int): Flow<List<UserWithoutSecureInfo>> {
        return userDao.getAllUsersByLocationIdStream(id)
    }

    override fun getUserStream(id: Int): Flow<UserWithoutSecureInfo> {
        return userDao.getUserStream(id)
    }

    override fun getAllUsersStream(): Flow<List<UserWithoutSecureInfo>> {
        return userDao.getAllUsersStream()
    }

    override fun getUserAllInfoStream(id: Int): Flow<User> {
        return userDao.getUserAllInfoStream(id)
    }

    // ✅ Add the login function here
    override suspend fun loginUser(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }
}
