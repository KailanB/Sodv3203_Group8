package com.example.skillswapapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skillswapapp.ui.FriendsScreen


import com.example.skillswapapp.ui.HomeScreen
import com.example.skillswapapp.ui.ProfileScreen
import com.example.skillswapapp.ui.SwapsScreen

@Composable
fun SkillSwapNavBar(
    navController: NavController,
    //navigateBack: () -> Unit,
    modifier: Modifier = Modifier
){
    BottomAppBar(
//        content = Text(text = "Header")
        actions = {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(Icons.Default.Home, contentDescription = "Home")
            }
            IconButton(onClick = { navController.navigate("profile") }) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
            }
            IconButton(onClick = { navController.navigate("swaps") }) {
                Icon(Icons.Default.Done, contentDescription = "Swaps")
            }
            IconButton(onClick = { navController.navigate("friends") }) {
                Icon(Icons.Default.Person, contentDescription = "Friends")
            }

        },
        modifier = modifier

    )



}


@Composable
fun SkillSwapApp(
    //viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
){
    Scaffold(
        bottomBar = {
            SkillSwapNavBar(
                navController = navController,
            )
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)

        ) {
            composable(route = "home") {
                HomeScreen(
                    modifier = Modifier
                )
            }
            composable(route = "profile") {
                ProfileScreen(
                    modifier = Modifier
                )
            }
            composable(route = "swaps") {
                SwapsScreen(
                    modifier = Modifier
                )
            }
            composable(route = "friends") {
                FriendsScreen(
                    modifier = Modifier
                )
            }

        }

    }

}

