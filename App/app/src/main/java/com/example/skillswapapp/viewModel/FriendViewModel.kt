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
                val userWithFriends = friendshipRepository.getUserWithFriends(userId)
                val pendingRequests = friendshipRepository.getPendingFriendRequests(userId)
                val friendList = userWithFriends.friends.map {
                    UserFriendList(
                        user_id = it.user_id,
                        name = it.name,
                        email = it.email,
                        profile_intro = it.profile_intro
                    )
                }

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
}
