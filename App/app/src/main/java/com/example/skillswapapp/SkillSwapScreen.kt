package com.example.skillswapapp


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skillswapapp.data.dao.FriendshipDao
import com.example.skillswapapp.view.FriendsScreen
import com.example.skillswapapp.view.HomeScreen
import com.example.skillswapapp.view.ProfileScreen
import com.example.skillswapapp.view.SwapsScreen
import com.example.skillswapapp.viewModel.FriendsViewModel
import com.example.skillswapapp.viewModel.UsersViewModel


@Composable
fun SkillSwapNavBar(
    navController: NavController,
    //navigateBack: () -> Unit,
    modifier: Modifier = Modifier
){
    BottomAppBar(
//        content = Text(text = "Header")
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
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
                val usersViewModel: UsersViewModel = viewModel()
                usersViewModel.getAllUsers()
                HomeScreen(
                    viewModel = usersViewModel,
                    modifier = Modifier
                )
            }
            composable(route = "profile") {
                val usersViewModel: UsersViewModel = viewModel()
                usersViewModel.getUserById()
                ProfileScreen(
                    viewModel = usersViewModel,
                    modifier = Modifier
                )
            }
            composable(route = "swaps") {
                SwapsScreen(
                    modifier = Modifier
                )
            }

            composable(route = "friends?user_id={user_id}") { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("user_id")?.toInt() ?: 0
                val friendsViewModel: FriendsViewModel = viewModel()

                // Pass the necessary parameters to FriendsScreen
                FriendsScreen(
                    viewModel = friendsViewModel,
                    userId = userId,
                    modifier = Modifier
                )
            }

        }

    }

}

