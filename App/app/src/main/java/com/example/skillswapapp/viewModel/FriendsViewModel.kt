package com.example.skillswapapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.relations.UserFriendList
import com.example.skillswapapp.data.repository.FriendshipRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FriendsViewModel(private val repository: FriendshipRepository) : ViewModel() {

    // Expose a stream of all friends for a specific user
    fun getAllFriends(userId: Int): Flow<List<UserFriendList>> {
        return repository.getAllFriendshipsByIdStream(userId)
    }

    // Handle accepting a friend request
    fun acceptFriendRequest(userId: Int, friendId: Int) {
        viewModelScope.launch {
            // Change the friendship status to 'accepted'
            val friendship = Friendship(userId, friendId, "accepted")
            repository.updateFriendship(friendship)
        }
    }

    // Handle deleting a friend
    fun deleteFriend(userId: Int, friendId: Int) {
        viewModelScope.launch {
            val friendship = Friendship(userId, friendId, "accepted")
            repository.deleteFriendship(friendship)
        }
    }

    // Get specific friendship by user ID
    fun getFriendship(userId: Int): Flow<Friendship?> {
        return repository.getFriendshipStream(userId)
    }
}