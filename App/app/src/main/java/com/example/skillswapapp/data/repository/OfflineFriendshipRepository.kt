package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.FriendshipDao
import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.relations.UserFriendList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

data class OfflineFriendshipRepository(
    private val friendshipDao: FriendshipDao
): FriendshipRepository {

    override suspend fun insertFriendship(friendship: Friendship) = friendshipDao.insert(friendship)
    override suspend fun updateFriendship(friendship: Friendship) = friendshipDao.update(friendship)
    override suspend fun deleteFriendship(friendship: Friendship) = friendshipDao.delete(friendship)

    override fun getAllFriendshipsByIdStream(id:Int): Flow<List<UserFriendList>> = friendshipDao.getAllFriendshipsById(id)
    override fun getFriendshipStream(id:Int): Flow<Friendship?> = friendshipDao.getFriendship(id)
    override suspend fun acceptFriendRequest(userId: Int, friendId: Int) {
        // Collect the friendship data, first() is used to collect the first element emitted by the flow
        val friendship = friendshipDao.getFriendshipByUserIdAndFriendId(userId, friendId).first()

        // Check if exist or not
        if (friendship != null) {
            val updatedFriendship = friendship.copy(status = "accepted")
            friendshipDao.update(updatedFriendship)  // Update status in the database
        }
    }

    override suspend fun deleteFriend(userId: Int, friendId: Int) {
        val friendship = friendshipDao.getFriendshipByUserIdAndFriendId(userId, friendId).first()

        // Check if exist or not
        if (friendship != null) {
            friendshipDao.delete(friendship)  // Deleted from database
        }
    }

}
