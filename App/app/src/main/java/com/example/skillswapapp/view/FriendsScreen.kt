package com.example.skillswapapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillswapapp.AppViewModelProvider
import com.example.skillswapapp.data.relations.UserFriendList
import com.example.skillswapapp.model.Friendship
import com.example.skillswapapp.state.FriendsUiState
import com.example.skillswapapp.viewModel.FriendViewModel
import com.example.skillswapapp.viewModel.FriendsViewModel

//
//@Composable
//fun FriendsScreen(
//    userId: Int, //for now just to check
//    viewModel: FriendsViewModel? = null, //ViewModel optional for testing,
//    modifier: Modifier = Modifier
//) {
//
//    val allFriendships = remember { mutableStateListOf<Friendship>() }
//    var friends by remember { mutableStateOf<List<UserFriendList>>(emptyList()) }
//    var friendRequests by remember { mutableStateOf <List<String>>(emptyList())}
////    var refreshTrigger by remember { mutableStateOf(0) }
//
//    // Collect the friends and friend requests
////    LaunchedEffect(userId) {
////        viewModel.getAllFriends(userId).collect { friendList ->
////            friends = friendList ?: emptyList()
////        }
////    }
////
////    LaunchedEffect(userId) {
////        viewModel.getFriendship(userId).collect { friendList ->
////            val validFriendList = friendList as? List<Friendship> ?: emptyList()
////            if (validFriendList.isNotEmpty()) {
////                friendRequests = validFriendList
////                    .filter { it.status == "pending" }
////                    .map { it.friend_id.toString() }
////            }
////        }
////    }
//
//    LaunchedEffect(userId) {
//        allFriendships.clear()
//        allFriendships.addAll(loadFriendships())
//        updateFriendAndRequestLists(
//            userId,
//            allFriendships,
//            { friends = it },
//            { friendRequests = it }
//        ) }
//
//    Column(modifier = modifier.padding(16.dp)) {
//        Text(
//            text = "Friends",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // New Friend Requests Section
//        if (friendRequests.isNotEmpty()) {
//            Text(text = "New Friend Request!", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
//            friendRequests.forEach { request ->
//                FriendRequestItem(request) {
////                   viewModel.acceptFriendRequest(userId, request.toInt())
//
//                    val index = allFriendships.indexOfFirst {
//                        it.user_Id == userId && it.friend_Id.toString() == request
//                    }
//                    if (index != -1) {
//                        allFriendships[index] = allFriendships[index].copy(status = "accepted")
//                        updateFriendAndRequestLists(userId, allFriendships, { friends = it }, { friendRequests = it })
//                    }
//                    println("Accepted friend request from user $request")
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // My Friends List Section
//        Text(text = "My Friends", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
//        if (friends.isEmpty()) {
//            Text(text = "No friends yet", fontSize = 16.sp, color = Color.Gray)
//        } else {
//            friends.forEach { friend ->
//                FriendItem(friend.name) {
////                    viewModel.deleteFriend(userId, friend.user_id)
//                    val index = allFriendships.indexOfFirst {
//                        it.user_Id == userId && it.friend_Id == friend.user_id
//                    }
//                    if (index != -1) {
//                        allFriendships.removeAt(index)
//                        updateFriendAndRequestLists(userId, allFriendships, { friends = it }, { friendRequests = it })
//                    }
//                    println("Deleted friend ${friend.user_id}")
//                }
//            }
//        }
//    }
//}
@Composable
fun FriendsScreen(
    userId: Int,
    viewModel: FriendViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.friendsUiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadFriends(userId)
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
                            // Handle Accept
                            println("Accepted friend request from ${friend.user_id}")
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
                            // Handle Delete
                            println("Deleted friend ${friend.user_id}")
                        }
                    }
                }
            }
        }
    }
}

fun updateFriendAndRequestLists(
    userId: Int,
    friendships: List<Friendship>,
    onFriendsUpdated: (List<UserFriendList>) -> Unit,
    onRequestsUpdated: (List<String>) -> Unit
) {
    val updatedFriends = friendships
        .filter { it.user_Id == userId && it.status == "accepted" }
        .map {
            UserFriendList(
                user_id = it.friend_Id,
                name = "Friend ${it.friend_Id}",
                email = "friend${it.friend_Id}@example.com",
                profile_intro = "Intro for friend ${it.friend_Id}"
            )
        }

    val updatedRequests = friendships
        .filter { it.user_Id == userId && it.status == "pending" }
        .map { it.friend_Id.toString() }

    onFriendsUpdated(updatedFriends)
    onRequestsUpdated(updatedRequests)
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
