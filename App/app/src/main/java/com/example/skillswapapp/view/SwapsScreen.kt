package com.example.skillswapapp.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillswapapp.AppViewModelProvider
import com.example.skillswapapp.state.SwapUiState
import com.example.skillswapapp.viewModel.FriendViewModel
import com.example.skillswapapp.viewModel.SkillSwapRequestViewModel
import com.example.skillswapappimport.SessionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
//
@Composable
fun SwapsScreen(
    navigateToEditUser: () -> Unit,
    modifier: Modifier = Modifier,
    sessionViewModel: SessionViewModel,
    viewModel: SkillSwapRequestViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.swapUiState.collectAsState()
    val currentUser by sessionViewModel.currentUser.collectAsState()
    val userId = currentUser?.user?.user_id

    LaunchedEffect(userId) {
        userId?.let { viewModel.loadSwapsForUser(it) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Swaps",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (uiState) {
            is SwapUiState.Loading -> {
                CircularProgressIndicator()
            }
            is SwapUiState.Error -> {
                val message = (uiState as SwapUiState.Error).message
                Text(text = "Error: $message")
            }
            is SwapUiState.Success -> {
                val swaps = (uiState as SwapUiState.Success).swapRequests
                val pendingSwaps = swaps.filter { it.request_status == "pending" }
                val acceptedSwaps = swaps.filter { it.request_status == "accepted" }

                if (pendingSwaps.isNotEmpty()) {
                    Text("Pending Requests", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    pendingSwaps.forEach { swap ->
                        SwapRequestCard(
                            name = swap.name,
                            offering = "Offering Skill ID: ${swap.user_id_from}",
                            requesting = "Requesting Skill ID: ${swap.user_id_to}",
                            dateTime = formatTime(swap.appointment_time),
                            message = swap.details,
                            onAccept = { viewModel.acceptSwapRequest(swap.request_id) },
                            onDecline = { viewModel.declineSwapRequest(swap.request_id) }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                if (acceptedSwaps.isNotEmpty()) {
                    Text("Accepted Swaps", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    acceptedSwaps.forEach { swap ->
                        AcceptedSwapCard(
                            name = swap.name,
                            offering = "Offering Skill ID: ${swap.user_id_from}",
                            requesting = "Requesting Skill ID: ${swap.user_id_to}",
                            dateTime = formatTime(swap.appointment_time),
                            onViewProfile = {
                                // navigateToProfile(swap.user_id_to)
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                if (pendingSwaps.isEmpty() && acceptedSwaps.isEmpty()) {
                    Text("No current swaps.")
                }
            }
        }
    }
}

fun formatTime(timeMillis: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy - h:mm a", Locale.getDefault())
    return formatter.format(Date(timeMillis))
}

@Composable
fun SwapRequestCard(name: String, offering: String, requesting: String, dateTime: String, message: String, onAccept: (() -> Unit)? = null,
                    onDecline: (() -> Unit)? = null) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }

            Text(text = "$offering For $requesting")
            Text(text = dateTime)

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = message)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (onDecline != null) {
                            Button(
                                onClick = onDecline,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2))
                            ) {
                                Text("Decline")
                            }
                        }
                        if (onAccept != null) {
                            Button(
                                onClick = onAccept,
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF9C4))
                            ) {
                                Text("Accept")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AcceptedSwapCard(
    name: String,
    offering: String,
    requesting: String,
    dateTime: String,
    onViewProfile: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }

            Text(text = "$offering For $requesting")
            Text(text = dateTime)

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Looking forward to this swap!")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onViewProfile,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBBDEFB))
                    ) {
                        Text("View Profile")
                    }
                }
            }
        }
    }
}

@Composable
fun UpcomingSwapCard(name: String, offering: String, requesting: String, dateTime: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }

            Text(text = "$offering For $requesting")
            Text(text = dateTime)

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Prepare for this upcoming swap! Be sure to bring your A-game.")
                }
            }
        }
    }
}