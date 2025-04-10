package com.example.skillswapapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.skillswapapp.viewModel.HomeViewModel
import com.example.skillswapapp.viewModel.ProfileViewModel
import com.example.skillswapapp.viewModel.UserEntryViewModel
import com.example.skillswapapp.viewModel.UsersViewModel
import com.example.skillswapappimport.SessionViewModel


object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            SessionViewModel(
                skillSwapApplication().container.userRepository,
                skillSwapApplication().container.userSkillRepository,
                skillSwapApplication().container.userSeeksSkillsRepository
            )
        }

        initializer {
            UserEntryViewModel(
                skillSwapApplication().container.userRepository,
                skillSwapApplication().container.locationRepository,
                skillSwapApplication().container.userSkillRepository,
                skillSwapApplication().container.userSeeksSkillsRepository
            )
        }


        initializer {
            HomeViewModel(
                skillSwapApplication().container.categoryRepository
            )

        }

        initializer {
            UsersViewModel(
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
    }
}

fun CreationExtras.skillSwapApplication(): SkillSwapApplication =
    (this[androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SkillSwapApplication)