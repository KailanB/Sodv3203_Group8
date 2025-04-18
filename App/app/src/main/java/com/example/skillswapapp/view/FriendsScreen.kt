package com.example.skillswapapp.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillswapapp.AppViewModelProvider
import com.example.skillswapapp.data.relations.UserFriendList
import com.example.skillswapapp.data.repository.iRepositories.FriendshipRepository
import com.example.skillswapapp.model.Friendship
import com.example.skillswapapp.state.FriendsUiState
import com.example.skillswapapp.viewModel.FriendViewModel
import com.example.skillswapapp.viewModel.FriendsViewModel
import com.example.skillswapappimport.SessionViewModel

@Composable
fun FriendsScreen(
    navigateToEditUser: () -> Unit,
    sessionViewModel: SessionViewModel,
    viewModel: FriendViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.friendsUiState.collectAsState()
    val currentUser by sessionViewModel.currentUser.collectAsState()
    val userId = currentUser?.user?.user_id

    LaunchedEffect(userId) {
        userId?.let { viewModel.loadFriends(it) }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Friends",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (uiState) {
            is FriendsUiState.Loading -> {
                Text("Loading...")
            }

            is FriendsUiState.Error -> {
                Text("Error: ${(uiState as FriendsUiState.Error).message}", color = Color.Red)
            }

            is FriendsUiState.Success -> {
                val friends = (uiState as FriendsUiState.Success).friendList
                val friendRequests = (uiState as FriendsUiState.Success).pendingFriendRequests

                if (friendRequests.isNotEmpty()) {
                    Text("New Friend Request!", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    friendRequests.forEach { friend ->
                        FriendRequestItem(friend.name) {
                            userId?.let {
                                viewModel.acceptFriendRequest(it, friend.user_id)
                                println("Accepted friend request from ${friend.user_id}")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("My Friends", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                if (friends.isEmpty()) {
                    Text("No friends yet", fontSize = 16.sp, color = Color.Gray)
                } else {
                    friends.forEach { friend ->
                        FriendItem(friend.name) {
                            userId?.let {
                                viewModel.deleteFriend(it, friend.user_id)
                                println("Deleted friend ${friend.user_id}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FriendRequestItem(name: String, onAccept: () -> Unitg) {
    var clicked by remember { mutableStateOf(false) }

    // Animate the scale of the button when clicked
    val scale by animateFloatAsState(
        targetValue = if (clicked) 1.2f else 1f,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = Color(0xFF1D3557), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name, fontSize = 16.sp)

        Button(
            onClick = {
                clicked = true
                onAccept()
            },
            modifier = Modifier.scale(scale)
        ) {
            Text(text = if (clicked) "Friend Added" else "Add Friend")
        }
    }
}


@Composable
fun FriendItem(name: String, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = Color(0xFF1D3557), RoundedCornerShape(8.dp))
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
