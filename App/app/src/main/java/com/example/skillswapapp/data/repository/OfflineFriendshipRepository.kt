package com.example.skillswapapp.data.repository

import com.example.skillswapapp.data.dao.FriendshipDao
import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.relations.UserFriendList
import com.example.skillswapapp.data.relations.UserWithFriends
import com.example.skillswapapp.data.repository.iRepositories.FriendshipRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

data class OfflineFriendshipRepository(
    private val friendshipDao: FriendshipDao
) : FriendshipRepository {

    override suspend fun insertFriendship(friendship: Friendship) {
        friendshipDao.insert(friendship)
    }

    override suspend fun updateFriendship(friendship: Friendship) {
        friendshipDao.update(friendship)
    }

    override suspend fun deleteFriendship(friendship: Friendship) {
        friendshipDao.delete(friendship)
    }

    // Get a stream of all friendships for a user by ID
    override fun getAllFriendshipsByIdStream(id: Int): Flow<List<UserFriendList>> {
        return friendshipDao.getAllFriendshipsById(id)
    }

    // Get a stream of a specific friendship by user ID
    override fun getFriendshipStream(id: Int): Flow<Friendship?> {
        return friendshipDao.getFriendship(id)
    }

    // Accept a friend request by updating the status of the friendship
    override suspend fun acceptFriendRequest(userId: Int, friendId: Int, status: String) {
        val friendship = friendshipDao.getFriendshipByUserIdAndFriendId(userId, friendId).first()
        if (friendship != null) {
            val updatedFriendship = friendship.copy(status = status)
            friendshipDao.update(updatedFriendship)
        }
    }
    // Delete a friendship by user ID and friend ID
    override suspend fun deleteFriend(userId: Int, friendId: Int) {
        val friendship = friendshipDao.getFriendshipByUserIdAndFriendId(userId, friendId).first()
        if (friendship != null) {
            friendshipDao.delete(friendship)
        }
    }

    override suspend fun getUserWithFriends(userId: Int): UserWithFriends {
        val friendsList = friendshipDao.getUserWithFriends(userId).first() // Collect the first value emitted by the Flow
        return friendsList.firstOrNull() ?: throw Exception("No friends found for the user")
    }

    override suspend fun getPendingFriendRequests(userId: Int): List<UserFriendList> {
        return friendshipDao.getPendingFriendRequests(userId).first()
    }
}
