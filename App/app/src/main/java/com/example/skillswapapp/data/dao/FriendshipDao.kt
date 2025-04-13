package com.example.skillswapapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.relations.UserFriendList
import com.example.skillswapapp.data.relations.UserWithFriends
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendshipDao {

    @Insert
    suspend fun insertFriendships(friendships: List<Friendship>)

    @Insert
    suspend fun insert(friendship: Friendship)

    @Update
    suspend fun update(friendship: Friendship)

    @Delete
    suspend fun delete(friendship: Friendship)

    // in theory should return a list of names and emails etc. of :id's friends
    // the join is because a users ID MAY be on the friend ID side or the user ID side
    // so we get a list of all users connected to user_id x, where they are on either side of the foreign key relationship
    @Query(
        "SELECT u.name, u.email, u.profile_intro, u.user_id, f.status FROM friendship f JOIN user u ON f.friend_id = u.user_id" +
                " WHERE f.user_id = :id" +
                " UNION " +
                "SELECT u.name, u.email, u.profile_intro, u.user_id, f.status FROM friendship f JOIN user u ON f.user_id = u.user_id" +
                " WHERE f.friend_id = :id AND f.status = 'pending'"

    )
    fun getAllFriendshipsById(id:Int): Flow<List<UserFriendList>>

    @Query(
        "SELECT * FROM friendship WHERE user_id = :id"
    )
    fun getFriendship(id:Int): Flow<Friendship>

    @Query("SELECT * FROM friendship WHERE user_id = :userId AND friend_id = :friendId")
    fun getFriendshipByUserIdAndFriendId(userId: Int, friendId: Int):  Flow<Friendship>

    @Query(
        "SELECT u.* FROM friendship f JOIN user u ON f.friend_id = u.user_id WHERE f.user_id = :userId " +
                "UNION " +
                "SELECT u.* FROM friendship f JOIN user u ON f.user_id = u.user_id WHERE f.friend_id = :userId"
    )
    fun getUserWithFriends(userId: Int): Flow<List<UserWithFriends>>
    // Get all pending friend requests (assuming 'status' is a column in your 'Friendship' entity)
    @Query(
        "SELECT u.user_id, u.name, u.email, u.profile_intro, f.status FROM friendship f " +
                "JOIN user u ON f.friend_id = u.user_id " +
                "WHERE f.user_id = :userId AND f.status = 'pending'"
    )
    fun getPendingFriendRequests(userId: Int): Flow<List<UserFriendList>>

}