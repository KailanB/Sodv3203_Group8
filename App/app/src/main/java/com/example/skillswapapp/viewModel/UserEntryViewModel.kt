package com.example.skillswapapp.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.entities.Location
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.entities.UserSeeksSkills
import com.example.skillswapapp.data.entities.UserSkills
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails
import com.example.skillswapapp.data.repository.iRepositories.CategoryRepository
import com.example.skillswapapp.data.repository.iRepositories.LocationRepository
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSeeksSkillsRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSkillsRepository
import com.example.skillswapapp.model.UiDisplaySkill
import com.example.skillswapapp.model.UiUserProfileDisplay
import com.example.skillswapapp.state.UserEntryCategorySkillsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserEntryViewModel(
    private val userRepository: UserRepository,
    private val locationRepository: LocationRepository,
    private val userSkillRepository: UserSkillsRepository,
    private val userSeeksSkillsRepository: UserSeeksSkillsRepository,
    private val categoryRepository: CategoryRepository
) :ViewModel() {


    private var _categorySkillsUiState = MutableStateFlow<UserEntryCategorySkillsUiState>(UserEntryCategorySkillsUiState.Loading)
    var categorySkillsUiState: StateFlow<UserEntryCategorySkillsUiState> = _categorySkillsUiState

    var userUiState by mutableStateOf(UserUiState())
        private set



    init {
        getAllCategories()
    }

    fun getAllCategories() {
        _categorySkillsUiState.value = UserEntryCategorySkillsUiState.Loading
        viewModelScope.launch {
            try {
                categoryRepository.getCategoriesWithSkillsStream().map { UserEntryCategorySkillsUiState.Success(it) }
                    .collect { uiState ->
                        _categorySkillsUiState.value = uiState
                    }


            } catch (exception: Exception) {
                Log.d("ERROR123", "getAllCategories: ERROR")
                _categorySkillsUiState.value = UserEntryCategorySkillsUiState.Error
            }
        }

    }


    fun updateUiState(userDetails: UserDetails) {
        userUiState = userUiState.copy(
            userDetails = userDetails,
            isEntryValid = validateInput(userDetails)
        )
    }

    fun updateLocationUiState(locationDetails: LocationDetails) {
        userUiState = userUiState.copy(
            locationDetails = locationDetails
        )
    }

    fun updateMySkillUiState(mySkillDetails: UiDisplaySkill) {
        userUiState = userUiState.copy(
            mySkillDetails = mySkillDetails
        )
    }
    fun updateMySeekingSkillUiState(mySkillSeekingDetails: UiDisplaySkill) {
        userUiState = userUiState.copy(
            skillSeekingDetails = mySkillSeekingDetails
        )
    }

    fun validateInput(uiState: UserDetails = userUiState.userDetails): Boolean {

        return with(uiState) {
            name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && reTypePassword.isNotBlank() &&
            (password == reTypePassword)
        }
    }
    fun validateLocationInput(uiState: LocationDetails = userUiState.locationDetails): Boolean {

        return with(uiState) {
            city.isNotBlank() && province.isNotBlank()
        }
    }

    suspend fun saveUser(){
        try {
            if(validateInput(userUiState.userDetails) && validateLocationInput(userUiState.locationDetails)){
                userRepository.updateUser(userUiState.userDetails.toUser())
                locationRepository.updateLocation(userUiState.locationDetails.toLocation())
            }
        } catch (exception: Exception) {
            Log.d("ERROR", "update user error")
        }
    }

    fun deleteMySkill(skill: UiDisplaySkill) {
        viewModelScope.launch {
            try {
                val currentUserId = userUiState.userDetails.id
                // just a safety check to be sure.
                // however a user should not have access to profile page or profile view model unless logged in
                // this check will be handled before a user navigates to profile
                val entity = skill.toUserSkills(currentUserId)
                userSkillRepository.deleteUserSkills(entity)

            } catch (exception: Exception) {

                Log.d("ERROR", "delete skill error")
            }


        }
    }
    fun deleteSkillSeeking(skill: UiDisplaySkill) {
        viewModelScope.launch {
            try {
                val currentUserId = userUiState.userDetails.id
                if(currentUserId != null)
                {
                    val entity = skill.toUserSeeksSkills(currentUserId)
                    userSeeksSkillsRepository.deleteUserSeeksSkills(entity)
                }
            } catch (exception: Exception) {

                Log.d("ERROR", "delete skill seeking error")
            }

        }
    }

    fun addNewUserSkill() {
        viewModelScope.launch {
            try {
                val currentUserId = userUiState.userDetails.id
                userSkillRepository.insertUserSkills(
                    userUiState.mySkillDetails.toUserSkills(currentUserId)
                )
            } catch (exception: Exception) {

                Log.d("ERROR", "add new skill error")
            }

        }
    }
    fun addNewUserSeekingSkill() {
        viewModelScope.launch {
            try {
                val currentUserId = userUiState.userDetails.id
                userSeeksSkillsRepository.insertUserSeeksSkills(
                    userUiState.skillSeekingDetails.toUserSeeksSkills(currentUserId)
                )
            } catch (exception: Exception) {

                Log.d("ERROR", "add new skill seeking error")
            }

        }
    }


    fun initializeForEdit(
        user: UiUserProfileDisplay?
    ) {
        if (user != null) {
            userUiState = toUserUiState(
                isEntryValid = true,
                user
            )
        }
    }


}



data class UserUiState(
    val userDetails: UserDetails = UserDetails(),
    val locationDetails: LocationDetails = LocationDetails(),
    val mySkillDetails: UiDisplaySkill = UiDisplaySkill(
        skillId = 0,
        skillName = "",
        description = "",
    ),
    val skillSeekingDetails: UiDisplaySkill = UiDisplaySkill(
        skillId = 0,
        skillName = "",
        description = "",
    ),
    val mySkills: List<UiDisplaySkill> = emptyList(),
    val skillSeeking: List<UiDisplaySkill> = emptyList(),
    val isEntryValid: Boolean = false
)

data class LocationDetails(
    val id: Int = 0,
    val city: String = "",
    val province: String = ""
)

fun LocationDetails.toLocation(): Location = Location(
    location_id = id,
    city = city,
    province = province
)

fun Location.toLocationDetails(): LocationDetails = LocationDetails(
    id = location_id,
    city = city,
    province = province
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

fun toUserUiState(
    isEntryValid: Boolean = false,
    user: UiUserProfileDisplay
): UserUiState = UserUiState(
    userDetails = user.user.toUserDetails(),
    locationDetails = user.location.toLocationDetails(),
    mySkills = user.skills.map { it.toUiDisplaySkill() },
    skillSeeking = user.seeksSkills.map { it.toUiDisplaySkill() },



    isEntryValid = isEntryValid
)


fun User.toUserDetails(): UserDetails = UserDetails (

    id = user_id,
    name = name,
    email = email,
    password = password,
    reTypePassword = password,
    profileIntro = profile_intro,
    description = description,
    preferences = preferences,
    locationId = location_id,

    profilePicture = profile_picture

)



fun UiDisplaySkill.toUserSkills(userId: Int): UserSkills {
    return UserSkills(
        skill_id = skillId,
        user_id = userId,
        skill_description = description
    )
}

fun UiDisplaySkill.toUserSeeksSkills(userId: Int): UserSeeksSkills {
    return UserSeeksSkills(
        skill_id = skillId,
        user_id = userId,
        skill_seekers_description = description
    )
}

fun UserSkillDetails.toUiDisplaySkill(): UiDisplaySkill {
    return UiDisplaySkill(
        skillId = userSkills.skill_id,
        skillName = skill_name,
        description = userSkills.skill_description
    )
}
fun UserSeeksSkillsDetails.toUiDisplaySkill(): UiDisplaySkill {
    return UiDisplaySkill(
        skillId = userSeeksSkills.skill_id,
        skillName = skill_name,
        description = userSeeksSkills.skill_seekers_description
    )
}