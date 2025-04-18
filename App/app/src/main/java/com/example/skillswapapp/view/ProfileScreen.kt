package com.example.skillswapapp.view

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    profileViewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
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
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "My Profile",
                // color = MaterialTheme.colorScheme.inversePrimary,
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            currentUser?.let {
                ProfileCard(currentUser!!, mySkills, skillsSeeking, modifier = Modifier.padding(20.dp))
            }
        }

        FloatingActionButton(
            onClick = {navigateToEditUser(currentUser?.user)},
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)


        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile"
            )
        }


    }
}


@Composable
fun ProfileCard(
    currentUser: UiUserProfileDisplay,
    mySkills: List<UiDisplaySkill>,
    skillsSeeking: List<UiDisplaySkill>,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier

        ) {
            Text(
                text = currentUser.user.name,
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = currentUser.user.email,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "About Me: " + currentUser.user.profile_intro,
                style = MaterialTheme.typography.titleLarge,
            )

            if(currentUser.user.description != null ||  currentUser.user.description != ""){
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text =  "Description: " + currentUser.user.description,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier
                )
            }
            if(currentUser.user.preferences != null ||  currentUser.user.preferences != ""){
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text =  "Preferences: " + currentUser.user.preferences,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            ProfileSkillList(mySkills, "My Skills:")
            Spacer(modifier = Modifier.height(20.dp))
            ProfileSkillList(skillsSeeking, "Seeking Skills:")

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
//        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()

    )
    {
        Text(
            text = skillListTitle,
            style = MaterialTheme.typography.titleLarge
        )
        Column( ) {
            skills.forEach { skill ->
                Column(){

                    Row(){
                        SkillCard(skill)
                    }
                    if(skill.description != "" && skill.description != null)
                    {
                        Row(){
                            Text (
                                text = skill.description
                            )
                        }
                    }

                }


            }

        }
    }

}




