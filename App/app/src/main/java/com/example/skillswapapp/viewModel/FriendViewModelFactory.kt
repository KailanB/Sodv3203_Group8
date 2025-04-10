package com.example.skillswapapp.viewModel

//factory class that helps create instances of your FriendshipViewModel, especially when your ViewModel has constructor parameters
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skillswapapp.data.dao.FriendshipDao
import com.example.skillswapapp.data.repository.OfflineFriendshipRepository

class FriendViewModelFactory(private val friendshipDao: FriendshipDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendsViewModel::class.java)) {
            val repository = OfflineFriendshipRepository(friendshipDao)
            return FriendsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}