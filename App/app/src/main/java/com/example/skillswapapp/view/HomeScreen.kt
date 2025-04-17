package com.example.skillswapapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.skillswapapp.viewModel.HomeViewModel



import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillswapapp.AppViewModelProvider
import com.example.skillswapapp.model.UiDisplaySkill
import com.example.skillswapapp.model.UiUserDisplay
import com.example.skillswapapp.state.HomeUiState
import com.example.skillswapapp.ui.components.ErrorScreen
import com.example.skillswapapp.ui.components.LoadingScreen
import com.example.skillswapapp.ui.components.SkillCard
import com.example.skillswapappimport.SessionViewModel


@Composable
fun HomeScreen(
    sessionViewModel: SessionViewModel,
    navigateToViewUserProfile: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModelUser: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

){
    val homeUiState by viewModelUser.homeUiState.collectAsState()
    var searchInput by remember { mutableStateOf("") }

    val currentUser by sessionViewModel.currentUser.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "Home",
            color = MaterialTheme.colorScheme.inversePrimary,
            style = MaterialTheme.typography.displayLarge
        )
        TextField(
            value = searchInput,
            onValueChange = { searchInput = it },
            label = { Text("Search Users by Skill or Location") },
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        when (val state = homeUiState) {
            is HomeUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is HomeUiState.Success -> {

                // filter by user having skill.name of searchInput
                val filteredUsers = filterUsersBySearchInput(state.users, searchInput)

                UserList(
                    filteredUsers,
                    onViewProfileClick = navigateToViewUserProfile
                )
            }
            is HomeUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
        }

    }

}


@Composable
fun UserList(
    usersList: List<UiUserDisplay>,
    onViewProfileClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){

    LazyColumn {
        items(items = usersList, key = {it.user.user_id}) { user ->
            UserCard(
                userWithSkills = user,
                modifier = Modifier.padding(8.dp),
                onViewProfileClick = { onViewProfileClick(user.user.user_id)}

            )
        }
    }

}

@Composable
fun UserCard (
    userWithSkills: UiUserDisplay,
    modifier: Modifier = Modifier,
    onViewProfileClick: (Int) -> Unit
){

    Card(
        modifier = modifier
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .background(
                color = MaterialTheme.colorScheme.primaryContainer
                )
        ) {
            Text(
                text =  userWithSkills.user.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            Text(
                text =  "About Me: " + userWithSkills.user.profile_intro,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            Text(
                text =  "From: " + userWithSkills.user.city + " " + userWithSkills.user.province,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            SkillList(userWithSkills.skills, "My Skills:", modifier)
            SkillList(userWithSkills.seeksSkills, "Seeking Skills:", modifier)

            Button(
                onClick = { onViewProfileClick(userWithSkills.user.user_id) },
                modifier = Modifier.padding(10.dp)
            ){
                Text(text = "View Profile")
            }
        }
    }

}

@Composable
fun SkillList(
    skills: List<UiDisplaySkill>,
    skillListTitle: String,
    modifier: Modifier = Modifier
)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    )
    {
        Text(
            text = skillListTitle,
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(4.dp)
        ){
            skills.forEach { skill ->
                SkillCard(skill)
            }

        }
    }

}

fun filterUsersBySearchInput(users: List<UiUserDisplay>, searchInput: String): List<UiUserDisplay> {
    return if (searchInput.isNotEmpty()) {
        users.filter { user ->
            user.skills.any { skill ->
                skill.skillName.contains(searchInput, ignoreCase = true)
            } ||
            user.user.city.contains(searchInput, ignoreCase = true) ||
            user.user.province.contains(searchInput, ignoreCase = true)
        }
    } else {
        users
    }
}


