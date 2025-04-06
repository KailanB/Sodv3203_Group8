package com.example.skillswapapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.skillswapapp.viewModel.UsersViewModel



import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillswapapp.AppViewModelProvider
import com.example.skillswapapp.R
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo
import com.example.skillswapapp.model.Skill
import com.example.skillswapapp.model.User
import com.example.skillswapapp.state.HomeUiState
import com.example.skillswapapp.state.UsersUiState
import com.example.skillswapapp.viewModel.HomeViewModel


@Composable
fun HomeScreen(
    viewModelUser: UsersViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier,
    viewModelHome: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

){
    val homeUiState by viewModelHome.homeUiState.collectAsState()
    val userUiState by viewModelUser.usersUiState.collectAsState()

    var searchInput by remember { mutableStateOf("") }


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
            label = { Text("Search Users by Skill") },
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
        )


        // ****************** TESTING ONLY ************************
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "TESTING ONLY",
                color = MaterialTheme.colorScheme.inversePrimary,
                style = MaterialTheme.typography.displaySmall
            )

            when(val currentState = homeUiState)
            {
                is HomeUiState.Success -> {
                    Text(text = currentState.categories[0].category)
                    Text(text = "Success?")
                }


                is HomeUiState.Loading -> {Text(text = "Loading")}
                is HomeUiState.Error -> {Text(text = "Error?")}

            }
            Text(
                text = "TESTING ONLY",
                color = MaterialTheme.colorScheme.inversePrimary,
                style = MaterialTheme.typography.displaySmall
            )

        }

        // ****************** TESTING ONLY ************************







        Spacer(modifier = Modifier.height(16.dp))
        when (val state = userUiState) {
            is UsersUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is UsersUiState.Success -> {

                // filter by user having skill.name of searchInput
                val filteredUsers = if (searchInput.isNotEmpty()) {
                    state.users
//                        .filter { user ->
//                        user.userSkills.any { skill ->
//                            skill.name.contains(searchInput, ignoreCase = true)
//                        }
//                    }
                }
                else {
                    state.users
                }
                UserList(state.users)
            }
            is UsersUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
        }

        // Add Sign Up and Login buttons
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier.fillMaxWidth().padding(16.dp)
//        ) {
//            Button(
//                onClick = { /* Handle Sign Up here */ },
//                modifier = Modifier.padding(8.dp)
//            ) {
//                Text(text = "Sign Up")
//            }
//
//            Button(
//                onClick = { /* Handle Login here */ },
//                modifier = Modifier.padding(8.dp)
//            ) {
//                Text(text = "Login")
//            }
//        }
    }

}


@Composable
fun UserList(
    usersList: List<UserWithoutSecureInfo>,
    modifier: Modifier = Modifier
){

    LazyColumn {
        items(items = usersList) { user ->
            UserCard(
                user = user,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}

@Composable
fun UserCard (user: UserWithoutSecureInfo, modifier: Modifier = Modifier){

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
                text =  user.name + " - User ID: " + user.user_id,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            Text(
                text =  user.email,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            Text(
                text =  "Description: " + user.profile_intro,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )
            //SkillList(user.userSkills, "My Skills:", modifier)
            //SkillList(user.userSeeksSkills, "Seeking Skills:", modifier)

        }
    }

}

@Composable
fun SkillList(
    skills: List<Skill>,
    description: String,
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
            text = description,
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


@Composable
fun SkillCard(
    skill: Skill
)
{
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = skill.name,
            // color = Color.White,
            // modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}


