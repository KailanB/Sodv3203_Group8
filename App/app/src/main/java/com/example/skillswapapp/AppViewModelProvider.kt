package com.example.skillswapapp

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.skillswapapp.viewModel.FriendViewModel
import com.example.skillswapapp.viewModel.ProfileViewModel
import com.example.skillswapapp.viewModel.UserEntryViewModel
import com.example.skillswapapp.viewModel.HomeViewModel
import com.example.skillswapapp.viewModel.SkillSwapRequestViewModel
import com.example.skillswapapp.viewModel.ViewUserProfileViewModel
//import com.example.skillswapapp.viewModel.FriendViewModel
import com.example.skillswapappimport.SessionViewModel


object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            SessionViewModel(
                userRepository =  skillSwapApplication().container.userRepository,
                userSkillsRepository =  skillSwapApplication().container.userSkillRepository,
                userSeeksSkillsRepository =  skillSwapApplication().container.userSeeksSkillsRepository,
                locationRepository =  skillSwapApplication().container.locationRepository
            )
        }

        initializer {
            UserEntryViewModel(
                userRepository =  skillSwapApplication().container.userRepository,
                locationRepository =  skillSwapApplication().container.locationRepository,
                userSkillRepository =  skillSwapApplication().container.userSkillRepository,
                userSeeksSkillsRepository =  skillSwapApplication().container.userSeeksSkillsRepository,
                categoryRepository = skillSwapApplication().container.categoryRepository
            )
        }

        initializer {
            ViewUserProfileViewModel(
                this.createSavedStateHandle(),
                userRepository =  skillSwapApplication().container.userRepository,
                userSkillsRepository =  skillSwapApplication().container.userSkillRepository,
                userSeeksSkillsRepository =  skillSwapApplication().container.userSeeksSkillsRepository
            )
        }



        initializer {
            HomeViewModel(
                skillSwapApplication().container.userRepository,
                skillSwapApplication().container.userSkillRepository,
                skillSwapApplication().container.userSeeksSkillsRepository
            )
        }

        initializer {
            ProfileViewModel(
                userRepository = skillSwapApplication().container.userRepository,
                userSkillsRepository = skillSwapApplication().container.userSkillRepository,
                userSeeksSkillsRepository = skillSwapApplication().container.userSeeksSkillsRepository
            )
        }

        initializer {
            FriendViewModel(
                friendshipRepository = skillSwapApplication().container.friendsRepository
            )
        }
        initializer {
            SkillSwapRequestViewModel(
                skillrequestRepository = skillSwapApplication().container.skillrequestRepository
            )
        }
    }
}

fun CreationExtras.skillSwapApplication(): SkillSwapApplication =
    (this[androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SkillSwapApplication)