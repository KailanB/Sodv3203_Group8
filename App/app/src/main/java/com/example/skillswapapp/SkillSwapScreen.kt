package com.example.skillswapapp


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skillswapapp.view.FriendsScreen
import com.example.skillswapapp.view.HomeScreen
import com.example.skillswapapp.view.ProfileScreen
import com.example.skillswapapp.view.SwapsScreen
import com.example.skillswapapp.view.UserDetailsDestination
import com.example.skillswapapp.view.UserEntryScreen
import com.example.skillswapapp.view.ViewUserProfileScreen
import com.example.skillswapappimport.SessionViewModel


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
                IconButton(
                    onClick = { navController.navigate("home") }
                ) {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Home",
                        modifier = Modifier.height(75.dp)
                            .width(75.dp),
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )
                }

                IconButton(
                    onClick = { navController.navigate("profile") }
                ) {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        modifier = Modifier.height(75.dp)
                            .width(75.dp),
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )
                }
                IconButton(
                    onClick = { navController.navigate("swaps") }
                ) {
                    Icon(
                        Icons.Default.Done,
                        contentDescription = "Swaps",
                        modifier = Modifier.height(75.dp)
                            .width(75.dp),
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )
                }
                IconButton(
                    onClick = { navController.navigate("friends/1") }
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Friends",
                        modifier = Modifier.height(75.dp)
                            .width(75.dp),
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )
                }
            }

        },
        modifier = modifier

    )



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillSwapApp(
    sessionViewModel: SessionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController()
){

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "SkillSwap") },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("CreateAccount") },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = "Sign Up")
                        }
                        Button(
                            onClick = { navController.navigate("LoginScreen") },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = "Login")
                        }
                    }
                }
            )
        },
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

                    sessionViewModel = sessionViewModel,
                    navigateToViewUserProfile = {
                        navController.navigate("${UserDetailsDestination.route}/${it}")
                    },
                    modifier = Modifier
                )
            }
            composable(
                route = UserDetailsDestination.routeWithArgs,
                arguments = listOf(navArgument("userId") {
                    type = NavType.IntType
                })

            ) {
                ViewUserProfileScreen(
                    sessionViewModel = sessionViewModel
                )
            }
            composable(route = "profile") {
                ProfileScreen(
                    navigateToEditUser = {navController.navigate(route = "userEntryScreen")},
                    sessionViewModel = sessionViewModel
                )
            }
            composable(route = "userEntryScreen"){
                val currentUser = sessionViewModel.currentUser.collectAsState()
                UserEntryScreen(
                    currentUser = currentUser.value
                )
            }



            composable(route = "swaps") {
                SwapsScreen(
                    modifier = Modifier
                )
            }
//            composable(route = "friends") {
//                FriendsScreen(
//                    modifier = Modifier
//                )
//            }
            composable(route = "friends/{userId}") { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: 0
                FriendsScreen(
                    userId = userId,
                    modifier = Modifier
                )
            }

        }

    }
}

