package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)

    fun getAllUsersBySkillIdStream(id:Int): Flow<List<UserWithoutSecureInfo>>

    fun getAllUsersByLocationIdStream(id:Int): Flow<List<UserWithoutSecureInfo>>
    fun getUserStream(id:Int): Flow<UserWithoutSecureInfo?>
    fun getAllUsersStream(): Flow<List<UserWithoutSecureInfo>>
    fun getUserAllInfoStream(id:Int): Flow<User>

}