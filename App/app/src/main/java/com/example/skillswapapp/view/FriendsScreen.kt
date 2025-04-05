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
import com.example.skillswapapp.data.entities.relations.UserFriendList
import com.example.skillswapapp.viewModel.FriendsViewModel


@Composable
fun FriendsScreen(
    userId: Int, //for now just to check
    viewModel: FriendsViewModel? = null, //ViewModel optional for testing,
    modifier: Modifier = Modifier
) {

    var friends by remember { mutableStateOf<List<UserFriendList>>(emptyList()) }
    var friendRequests by remember { mutableStateOf <List<String>>(emptyList())}


    // Collect the friends and friend requests
//    LaunchedEffect(userId) {
//        viewModel.getAllFriends(userId).collect { friendList ->
//            friends = friendList ?: emptyList()
//        }
//    }
//
//    LaunchedEffect(userId) {
//        viewModel.getFriendship(userId).collect { friendList ->
//            val validFriendList = friendList as? List<Friendship> ?: emptyList()
//            if (validFriendList.isNotEmpty()) {
//                friendRequests = validFriendList
//                    .filter { it.status == "pending" }
//                    .map { it.friend_id.toString() }
//            }
//        }
//    }

    LaunchedEffect(userId) {
        val allFriendships = loadFriendships()
        println("Loaded Friendships: $allFriendships") // Debug log

        // Filtering accepted friends for userId = 1
        friends = allFriendships
            .filter { it.user_Id == userId && it.status == "accepted" }
            .map {
                UserFriendList(
                    user_id = it.friend_Id,
                    name = "Friend ${it.friend_Id}",
                    email = "friend${it.friend_Id}@example.com",
                    profile_intro = "Intro for friend ${it.friend_Id}"
                )
            }

        // Filtering pending requests for userId = 1
        friendRequests = allFriendships
            .filter { it.user_Id == userId && it.status == "pending" }
            .map { it.friend_Id.toString() }

        println("Friends: $friends")
        println("Friend Requests: $friendRequests")
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
//                    viewModel.acceptFriendRequest(userId, request.toInt())
                    println("Accepted friend request from user $request") //test
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
//                    viewModel.deleteFriend(userId, friend.user_id)
                    println("Deleted friend ${friend.user_id}")
                }
            }
        }
    }
}

fun loadFriendships(): List<com.example.skillswapapp.model.Friendship> {
    return listOf(
        com.example.skillswapapp.model.Friendship(1, 2, "accepted"),
        com.example.skillswapapp.model.Friendship(1, 3, "pending"),
        com.example.skillswapapp.model.Friendship(2, 4, "accepted"),
        com.example.skillswapapp.model.Friendship(3, 5, "rejected"),
        com.example.skillswapapp.model.Friendship(4, 5, "accepted")
    )
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
