package com.example.skillswapapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.entities.Friendship
import com.example.skillswapapp.data.relations.UserFriendList
import com.example.skillswapapp.data.repository.iRepositories.FriendshipRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
//not in use
class FriendsViewModel(private val repository: FriendshipRepository) : ViewModel() {

}