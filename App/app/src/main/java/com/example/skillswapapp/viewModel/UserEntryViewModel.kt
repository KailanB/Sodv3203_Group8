package com.example.skillswapapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.repository.UserRepository

class UserEntryViewModel(private val userRepository: UserRepository) :ViewModel() {

    var userUiState by mutableStateOf(UserUiState())
        private set

    fun updateUiState(userDetails: UserDetails) {
        userUiState = UserUiState(userDetails = userDetails, isEntryValid = validateInput(userDetails))
    }

    fun validateInput(uiState: UserDetails = userUiState.userDetails): Boolean {

        return with(uiState) {
            name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && reTypePassword.isNotBlank() &&
            (password == reTypePassword)
        }
    }

    suspend fun saveUser(){
        if(validateInput(userUiState.userDetails)){
            userRepository.updateUser(userUiState.userDetails.toUser())
        }
    }


    fun initializeForEdit(user: User?) {
        if (user != null) {
            userUiState = user.toUserUiState(isEntryValid = true)
        }
    }


}

data class UserUiState(
    val userDetails: UserDetails = UserDetails(),
    val isEntryValid: Boolean = false
)

data class UserDetails(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val reTypePassword: String = "",
    val profileIntro: String? = "",
    val description: String? = "",
    val preferences: String? = "",
    val locationId: Int = 0,

    val profilePicture: ByteArray?  = null

)

fun UserDetails.toUser(): User = User(
    user_id = id,
    name = name,
    email = email,
    password = password,
    profile_intro = profileIntro,
    description = description,
    preferences = preferences,
    location_id = locationId,

    profile_picture = profilePicture
)

fun User.toUserUiState(isEntryValid: Boolean = false): UserUiState = UserUiState(
    userDetails = this.toUserDetails(),
    isEntryValid = isEntryValid
)

fun User.toUserDetails(): UserDetails = UserDetails (

    id = user_id,
    name = name,
    email = email,
    password = password,
    profileIntro = profile_intro,
    description = description,
    preferences = preferences,
    locationId = location_id,

    profilePicture = profile_picture

)