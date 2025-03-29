package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)

    fun getAllUsersBySkillIdStream(id:Int): Flow<List<User>>

    fun getAllUsersByLocationIdStream(id:Int): Flow<List<User>>
    fun getUserStream(id:Int): Flow<User?>
    fun getAllUsersStream(): Flow<List<User>>

}