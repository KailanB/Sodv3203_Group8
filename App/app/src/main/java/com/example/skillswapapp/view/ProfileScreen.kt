package com.example.skillswapapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillswapapp.AppViewModelProvider
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.model.UiUserProfileDisplay
import com.example.skillswapapp.model.UiDisplaySkill
import com.example.skillswapapp.ui.components.SkillCard
import com.example.skillswapapp.viewModel.ProfileViewModel
import com.example.skillswapappimport.SessionViewModel


@Composable
fun ProfileScreen(
    navigateToEditUser: (User?) -> Unit,
    sessionViewModel: SessionViewModel,
    profileViewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {

    val currentUser by sessionViewModel.currentUser.collectAsState()
    currentUser?.let { user ->
        LaunchedEffect(user) {
            profileViewModel.setUser(user)
            profileViewModel.setMySkills(user.skills)
            profileViewModel.setSkillsSeeking(user.seeksSkills)
        }
    }

    val mySkills by profileViewModel.mySkills.collectAsState(initial = emptyList())
    val skillsSeeking by profileViewModel.skillsSeeking.collectAsState(initial = emptyList())


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        FloatingActionButton(
            onClick = {navigateToEditUser(currentUser?.user)}
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile"
            )
        }
        Text(
            text = "Profile",
            color = MaterialTheme.colorScheme.inversePrimary,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        currentUser?.let {
            ProfileCard(currentUser!!, mySkills, skillsSeeking)
        }


//        when (val state = uiState) {
//            is UsersUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
//            is UsersUiState.Success -> {
//                if(state.user != null)
//                {
//                    ProfileCard(
//                        state.user
//                    )
//                }
//
//            }
//
//            is UsersUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
//        }
    }
}


@Composable
fun ProfileCard(
    currentUser: UiUserProfileDisplay,
    mySkills: List<UiDisplaySkill>,
    skillsSeeking: List<UiDisplaySkill>,
    modifier: Modifier = Modifier) {
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
                text = currentUser.user.name + " - User ID: " + currentUser.user.user_id,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            Text(
                text = currentUser.user.email,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )

            Text(
                text = "Description: " + currentUser.user.profile_intro,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
            )
            ProfileSkillList(mySkills, "My Skills:", modifier)
            ProfileSkillList(skillsSeeking, "Seeking Skills:", modifier)

        }
    }

}

@Composable
fun ProfileSkillList(
    skills: List<UiDisplaySkill>,
    skillListTitle: String,
    modifier: Modifier = Modifier
) {
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
        ) {
            skills.forEach { skill ->
                SkillCard(skill)
            }

        }
    }

}




