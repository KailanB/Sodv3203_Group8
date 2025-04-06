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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.skillswapapp.R
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo
import com.example.skillswapapp.model.Skill
import com.example.skillswapapp.model.User
import com.example.skillswapapp.state.UsersUiState
import com.example.skillswapapp.viewModel.UsersViewModel




@Composable
fun ProfileScreen(
    viewModel: UsersViewModel,
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.usersUiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Profile",
            color = MaterialTheme.colorScheme.inversePrimary,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is UsersUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is UsersUiState.Success -> {
                if(state.user != null)
                {
                    ProfileCard(
                        state.user
                    )
                }

            }

            is UsersUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
        }
    }
}


@Composable
fun ProfileCard(user: UserWithoutSecureInfo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
        ) {
            Text(
                text = user.name + " - User ID: " + user.user_id,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            Text(
                text = user.email,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            Text(
                text = "Description: " + user.profile_intro,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )
            //ProfileSkillList(user.userSkills, "My Skills:", modifier)
            //ProfileSkillList(user.userSeeksSkills, "Seeking Skills:", modifier)

        }
    }

}

@Composable
fun ProfileSkillList(
    skills: List<Skill>,
    description: String,
    modifier: Modifier = Modifier
) {
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
        ) {
            skills.forEach { skill ->
                SkillCard(skill)
            }

        }
    }

}




