package com.example.skillswapapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillswapapp.data.dao.FriendshipDao
import com.example.skillswapapp.data.entities.Friendship
import kotlinx.coroutines.flow.collect
import com.example.skillswapapp.data.entities.relations.UserFriendList
import com.example.skillswapapp.data.repository.FriendshipRepository
import com.example.skillswapapp.viewModel.FriendViewModelFactory
import com.example.skillswapapp.viewModel.FriendsViewModel



@Composable
fun FriendsScreen(
    userId: Int,
    viewModel: FriendsViewModel,
    modifier: Modifier = Modifier
) {

    var friends by remember { mutableStateOf<List<UserFriendList>>(emptyList()) }
    var friendRequests by remember { mutableStateOf <List<String>>(emptyList())}


    // Collect the friends and friend requests
    LaunchedEffect(userId) {
        viewModel.getAllFriends(userId).collect { friendList ->
            friends = friendList ?: emptyList()
        }
    }

    LaunchedEffect(userId) {
        viewModel.getFriendship(userId).collect { friendList ->
            val validFriendList = friendList as? List<Friendship> ?: emptyList()
            if (validFriendList.isNotEmpty()) {
                friendRequests = validFriendList
                    .filter { it.status == "pending" }
                    .map { it.friend_id.toString() }
            }
        }
    }


    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Friends",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // New Friend Requests Section
        if (friendRequests.isNotEmpty()) {
            Text(text = "New Friend Request!", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            friendRequests.forEach { request ->
                FriendRequestItem(request) {
                    viewModel.acceptFriendRequest(userId, request.toInt())
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // My Friends List Section
        Text(text = "My Friends", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        if (friends.isEmpty()) {
            Text(text = "No friends yet", fontSize = 16.sp, color = Color.Gray)
        } else {
            friends.forEach { friend ->
                FriendItem(friend.name) {
                    viewModel.deleteFriend(userId, friend.user_id)
                }
            }
        }
    }
}

@Composable
fun FriendRequestItem(name: String, onAccept: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name, fontSize = 16.sp)
        Button(onClick = onAccept) {
            Text(text = "Add Friend")
        }
    }
}

@Composable
fun FriendItem(name: String, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name, fontSize = 16.sp)
        Button(onClick = onDelete, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
            Text(text = "Delete", color = Color.White)
        }
    }
}
