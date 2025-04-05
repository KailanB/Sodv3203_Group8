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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SwapsScreen(
    modifier: Modifier = Modifier
){
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

        // Swap Requests Section
        SwapRequestCard(
            name = "Jake Peralta",
            offering = "Cooking",
            requesting = "Acting",
            dateTime = "On May 10, 5:00 p.m.",
            message = "Hi I’m Jake! I’d love to learn some Kotlin. Feel free to call me at 555-3344"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Upcoming Swaps Section
        UpcomingSwapCard(
            name = "Eleanor Shellstrop",
            offering = "Leadership",
            requesting = "Improv",
            dateTime = "On May 2, 7:30 p.m."
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Previous Swaps Section (Rating)
        Text(text = "Rate Previous Skill Swaps", fontSize = 18.sp)
        Text(text = "Kate Dibiasky", fontSize = 16.sp)
    }
}

@Composable
fun SwapRequestCard(name: String, offering: String, requesting: String, dateTime: String, message: String) {
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
                        Button(
                            onClick = { /* handle decline */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCDD2))
                        ) {
                            Text("Decline")
                        }
                        Button(
                            onClick = { /* handle accept */ },
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