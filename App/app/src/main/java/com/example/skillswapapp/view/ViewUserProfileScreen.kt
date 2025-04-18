package com.example.skillswapapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillswapapp.AppViewModelProvider
import com.example.skillswapapp.model.UiDisplaySkill
import com.example.skillswapapp.model.UiUserProfileDisplay
import com.example.skillswapapp.model.UiViewUserProfileDisplay
import com.example.skillswapapp.state.HomeUiState
import com.example.skillswapapp.state.ViewUserProfileUiState
import com.example.skillswapapp.ui.components.ErrorScreen
import com.example.skillswapapp.ui.components.LoadingScreen
import com.example.skillswapapp.ui.navigation.NavigationDestination
import com.example.skillswapapp.viewModel.HomeViewModel
import com.example.skillswapapp.viewModel.ViewUserProfileViewModel
import com.example.skillswapappimport.SessionViewModel

object UserDetailsDestination : NavigationDestination {
    override val route = "viewUserProfileScreen"
    override val titleRes = "View Profile"
    const val userIdArg = "userId"
    val routeWithArgs = "$route/{$userIdArg}"

}

@Composable
fun ViewUserProfileScreen(
    sessionViewModel: SessionViewModel,
    viewModelViewProfile: ViewUserProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val loggedInUser by sessionViewModel.currentUser.collectAsState()

    val viewUserProfileUiState by viewModelViewProfile.viewUserProfileUiState.collectAsState()


    Spacer(modifier = Modifier.height(16.dp))
    when (val state = viewUserProfileUiState) {
        is ViewUserProfileUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is ViewUserProfileUiState.Success -> {
                val mySkills = viewModelViewProfile.getMySkills()
                val skillsSeeking = viewModelViewProfile.getSkillsSeeking()
                ViewProfileCard(
                    state.userWithSkills,
                    mySkills,
                    skillsSeeking,
                    onAddFriendClick = { friendId ->
                        if(loggedInUser != null)
                        {
                            viewModelViewProfile.addNewFriend(myId = loggedInUser!!.user.user_id, friendId = friendId)
                        }

                    },
                    modifier = Modifier.padding(20.dp)
                )

        }
        is ViewUserProfileUiState.Error -> ErrorScreen( modifier = Modifier.fillMaxSize())
    }

}


@Composable
fun ViewProfileCard(
    currentUser: UiViewUserProfileDisplay,
    mySkills: List<UiDisplaySkill>,
    skillsSeeking: List<UiDisplaySkill>,
    onAddFriendClick: (Int) -> Unit,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        val scrollState = rememberScrollState()


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.verticalScroll(scrollState)

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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text =  "From: " + currentUser.user.city + " " + currentUser.user.province,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
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

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {onAddFriendClick(currentUser.user.user_id)},
                modifier = Modifier.padding(10.dp)
            ){
                Text(text = "Add Friend")
            }

        }
    }

}