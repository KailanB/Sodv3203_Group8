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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.skillswapapp.model.Users
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
import com.example.skillswapapp.R
import com.example.skillswapapp.model.Skill
import com.example.skillswapapp.state.UsersUiState


@Composable
fun HomeScreen(
    viewModel: UsersViewModel,
    modifier: Modifier = Modifier
){

    val uiState by viewModel.usersUiState.collectAsState()
    var searchInput by remember { mutableStateOf("") }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "Home",
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.displayLarge
        )
        TextField(
            value = searchInput,
            onValueChange = { searchInput = it },
            label = { Text("Search Users by Skill") },
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
        )


        Spacer(modifier = Modifier.height(16.dp))
        when (val state = uiState) {
            is UsersUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is UsersUiState.Success -> {

                // filter by user having skill.name of searchInput
                val filteredUsers = state.users.filter { user ->
                    user.userSkills.any { skill ->
                        skill.name == searchInput
                    }
                }
                UserList(filteredUsers)
            }
            is UsersUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
        }


        if (uiState is UsersUiState.Success)
        {
            val users = (uiState as UsersUiState.Success).users
            UserList(usersList = users)
        }
    }

}


@Composable
fun UserList(
    usersList: List<Users>,
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
fun UserCard (user: Users, modifier: Modifier = Modifier){

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
                text =  user.name + " - User ID: " + user.userId,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            Text(
                text =  user.email,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            Text(
                text =  "Description: " + user.description,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            SkillList(user.userSkills, "My Skills:", modifier)


            SkillList(user.userSeeksSkills, "Seeking Skills:", modifier)

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
            color = Color.White,
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


