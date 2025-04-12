package com.example.skillswapapp.state

import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.relations.UserFriendList
import com.example.skillswapapp.data.relations.UserWithFriends

sealed interface FriendsUiState {
    data class Success(
        val friendList: List<UserFriendList> = emptyList(),
        val pendingFriendRequests: List<UserFriendList> = emptyList(),
    ): FriendsUiState
    data class Error(val message: String) : FriendsUiState
    object Loading : FriendsUiState
}