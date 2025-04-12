package com.example.skillswapapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.relations.UserFriendList
import com.example.skillswapapp.data.repository.iRepositories.FriendshipRepository
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import com.example.skillswapapp.state.FriendsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FriendViewModel(
    private val friendshipRepository: FriendshipRepository
) : ViewModel() {

    private val _friendsUiState = MutableStateFlow<FriendsUiState>(FriendsUiState.Loading)
    val friendsUiState: StateFlow<FriendsUiState> = _friendsUiState

    fun loadFriends(userId: Int) {
        viewModelScope.launch {
            _friendsUiState.value = FriendsUiState.Loading


            try {
                val friendList = friendshipRepository.getAllFriendshipsByIdStream(userId).first()
                    .filter { it.status == "accepted" }
                val pendingRequests = friendshipRepository.getPendingFriendRequests(userId)

                _friendsUiState.value = FriendsUiState.Success(
                    friendList = friendList,
                    pendingFriendRequests = pendingRequests
                )
            } catch (e: Exception) {
                _friendsUiState.value = FriendsUiState.Error(
                    message = e.localizedMessage ?: "Unknown error"
                )
            }
        }
    }
    fun acceptFriendRequest(userId: Int, friendId: Int) {
        viewModelScope.launch {
            try {
                friendshipRepository.acceptFriendRequest(userId, friendId, "accepted")
                _friendsUiState.value = FriendsUiState.Loading
                loadFriends(userId)
            } catch (e: Exception) {
                _friendsUiState.value = FriendsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteFriend(userId: Int, friendId: Int) {
        viewModelScope.launch {
            friendshipRepository.deleteFriend(userId, friendId)
            loadFriends(userId)
        }
    }
}