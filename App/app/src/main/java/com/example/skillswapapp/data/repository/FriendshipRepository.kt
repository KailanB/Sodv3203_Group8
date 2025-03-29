package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.relations.UserFriendList
import kotlinx.coroutines.flow.Flow

interface FriendshipRepository {

    suspend fun insertFriendship(friendship: Friendship)
    suspend fun updateFriendship(friendship: Friendship)
    suspend fun deleteFriendship(friendship: Friendship)

    fun getAllFriendshipsByIdStream(id:Int): Flow<List<UserFriendList>>
    fun getFriendshipStream(id:Int): Flow<Friendship?>

}