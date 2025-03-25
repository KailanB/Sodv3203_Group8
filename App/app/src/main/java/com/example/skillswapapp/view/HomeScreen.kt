package com.example.skillswapapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.skillswapapp.model.Skill
import com.example.skillswapapp.model.UserSeeksSkills
import com.example.skillswapapp.model.Users
import com.example.skillswapapp.viewModel.UsersViewModel



import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.skillswapapp.state.UsersUiState


@Composable
fun HomeScreen(
    viewModel: UsersViewModel,
    modifier: Modifier = Modifier
){

    val uiState by viewModel.usersUiState.collectAsState()



    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "Users",
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (uiState is UsersUiState.Success)
        {
            val users = (uiState as UsersUiState.Success).users
            UserList(usersList = users)
        }
    }

}

@Composable
fun UserList(
    usersList: List<Users>
){

    LazyColumn {
        items(items = usersList) { user ->
            UserCard(
                user = user,
                modifier = Modifier.padding(12.dp)
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text =  "User ID: " + user.userId,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(12.dp)
            )

            Text(
                text =  user.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(12.dp)
            )

            Text(
                text =  user.email,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(12.dp)
            )

            Text(
                text =  "Description: " + user.description,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(12.dp)
            )

        }
    }


}


