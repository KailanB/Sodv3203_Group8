package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.FriendshipDao
import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.relations.UserFriendList
import kotlinx.coroutines.flow.Flow

data class OfflineFriendshipRepository(
    private val friendshipDao: FriendshipDao
): FriendshipRepository {

    override suspend fun insertFriendship(friendship: Friendship) = friendshipDao.insert(friendship)
    override suspend fun updateFriendship(friendship: Friendship) = friendshipDao.update(friendship)
    override suspend fun deleteFriendship(friendship: Friendship) = friendshipDao.delete(friendship)

    override fun getAllFriendshipsByIdStream(id:Int): Flow<List<UserFriendList>> = friendshipDao.getAllFriendshipsById(id)
    override fun getFriendshipStream(id:Int): Flow<Friendship?> = friendshipDao.getFriendship(id)

}
