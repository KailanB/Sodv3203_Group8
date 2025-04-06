package com.example.skillswapapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.entities.relations.UserWithoutSecureInfo
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert
    suspend fun insertUsers(users: List<User>)

    @Insert
    suspend fun insert(user:User)

    @Update
    suspend fun update(user:User)

    @Update
    suspend fun delete(user:User)

    @Query(
        "SELECT u.user_id, u.name, u.email, u.profile_intro, l.province, l.city, u.profile_picture FROM user u " +
                "JOIN location l ON l.location_id = u.location_id "

    )
    fun getAllUsers(): Flow<List<UserWithoutSecureInfo>>

    // get users by skill
    @Query(
        "SELECT u.user_id, u.name, u.email, u.profile_intro, l.province, l.city, u.profile_picture FROM user u JOIN userSkills us ON us.user_id = u.user_id " +
                "JOIN skill s ON s.skill_id = us.skill_id " +
                "JOIN location l ON l.location_id = u.location_id " +
                "WHERE us.skill_id = :id"
    )
    fun getAllUsersBySkillId(id:Int): Flow<List<UserWithoutSecureInfo>>

    // get users by location
    @Query(
        "SELECT u.user_id, u.name, u.email, u.profile_intro, u.profile_picture, l.province, l.city FROM user u " +
                "JOIN location l ON l.location_id = u.location_id " +
                "WHERE u.location_id = :id"
    )
    fun getAllUsersByLocationId(id:Int): Flow<List<UserWithoutSecureInfo>>

    @Query(
        "SELECT u.user_id, u.name, u.email, u.profile_intro, u.profile_picture, l.province, l.city FROM user u " +
                "JOIN location l ON l.location_id = u.location_id " +
                "WHERE u.user_id = :id"
    )
    fun getUser(id:Int): Flow<UserWithoutSecureInfo>

    // added part for user login -KK
    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?
}