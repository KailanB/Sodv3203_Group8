package com.example.skillswapapp.data.repository.iRepositories

import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.relations.UserFriendList
import com.example.skillswapapp.data.relations.UserWithFriends
import kotlinx.coroutines.flow.Flow

interface FriendshipRepository{

    suspend fun insertFriendship(friendship: Friendship)
    suspend fun updateFriendship(friendship: Friendship)
    suspend fun deleteFriendship(friendship: Friendship)

    fun getAllFriendshipsByIdStream(id:Int): Flow<List<UserFriendList>>
    fun getFriendshipStream(id:Int): Flow<Friendship?>

    suspend fun acceptFriendRequest(userId: Int, friendId: Int)
    suspend fun deleteFriend(userId: Int, friendId: Int)

    suspend fun getUserWithFriends(userId: Int): UserWithFriends
    suspend fun getPendingFriendRequests(userId: Int): List<UserFriendList>

}