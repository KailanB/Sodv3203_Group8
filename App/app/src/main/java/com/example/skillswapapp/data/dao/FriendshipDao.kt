package com.example.skillswapapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.relations.UserFriendList
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendshipDao {

    @Insert
    suspend fun insert(friendship: Friendship)

    @Update
    suspend fun update(friendship: Friendship)

    @Update
    suspend fun delete(friendship: Friendship)

    // in theory should return a list of names and emails etc. of :id's friends
    // the join is because a users ID MAY be on the friend ID side or the user ID side
    // so we get a list of all users connected to user_id x, where they are on either side of the foreign key relationship
    @Query(
        "SELECT u.name, u.email, u.profile_intro, u.user_id FROM friendship f JOIN user u ON f.friend_id = u.user_id" +
                " WHERE f.user_id = :id" +
                " UNION " +
                "SELECT u.name, u.email, u.profile_intro, u.user_id FROM friendship f JOIN user u ON f.user_id = u.user_id" +
                " WHERE f.friend_id = :id"

    )
    fun getAllFriendshipsById(id:Int): Flow<List<UserFriendList>>

    @Query(
        "SELECT * FROM friendship WHERE user_id = :id"
    )
    fun getFriendship(id:Int): Flow<Friendship>
}