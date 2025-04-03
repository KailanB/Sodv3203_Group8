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

@Composable
fun FriendsScreen(modifier: Modifier = Modifier) {
    var friends by remember { mutableStateOf(listOf("Eleanor Shellstrop", "Sophie Hatter", "Tommy Wiseau", "Kate Dibiasky")) }
    var friendRequests by remember { mutableStateOf(listOf("Jake Peralta")) }

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
                    friends = friends + request
                    friendRequests = friendRequests - request
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // My Friends List Section
        Text(text = "My Friends", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        friends.forEach { friend ->
            FriendItem(friend) {
                friends = friends - friend
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
