package com.example.skillswapapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails
import com.example.skillswapapp.data.repository.iRepositories.UserRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSeeksSkillsRepository
import com.example.skillswapapp.data.repository.iRepositories.UserSkillsRepository
import com.example.skillswapapp.model.UiDisplaySkill
import com.example.skillswapapp.model.UiUserDisplay
import com.example.skillswapapp.state.UsersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UsersViewModel (
    private val userRepository: UserRepository,
    private val userSkillsRepository: UserSkillsRepository,
    private val userSeeksSkillsRepository: UserSeeksSkillsRepository
): ViewModel() {

    private val _usersUiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val usersUiState: StateFlow<UsersUiState> = _usersUiState


    init {
        getAllUsers()
    }

    fun getAllUsers() {
        _usersUiState.value = UsersUiState.Loading


        // ******************* METHOD TO PULL FROM DATABASE *************
        viewModelScope.launch {
            try {
                val usersFlow = userRepository.getAllUsersStream()

                usersFlow.collect{userList ->
                    val usersWithSkills = userList.map { user ->
                        val skills =
                            userSkillsRepository.getAllUserSkillsByIdStream(user.user_id).first()
                        val seeksSkills =
                            userSeeksSkillsRepository.getAllUserSeeksSkillsByIdStream(user.user_id)
                                .first()

                        val uiSkills = skills.map { it.toUiDisplaySkill() }
                        val uiSkillsSeeking = seeksSkills.map { it.toUiDisplaySkill() }
                            UiUserDisplay(user, uiSkills, uiSkillsSeeking)

                        }
                        _usersUiState.value = UsersUiState.Success(users = usersWithSkills)


                }




//                { UsersUiState.Success(users = it) }
//                    .collect { uiState ->
//                        Log.d("UserList123", "Users: ${uiState.users}")
//                        _usersUiState.value = uiState
//                    }


            } catch (exception: Exception) {
                Log.d("ERROR123", "getAllCategories: ERROR")
                _usersUiState.value = UsersUiState.Error
            }
        }

        // ******************* OLD METHOD TO GET DUMMY STRING DATA  NOT FROM DATABASE ***************

//        try {
//            // this is later where
//            // val usersList = userRepository.getAllUsers()
//
//            // for now loading a list of dummy data to use
//            val usersList = DataSource().loadUsers()
//            // I am simply pulling dummy data with a pre-defined list for ID 1
//            // these get data methods need to COMPLETELY CHANGE!
//            // this would normally be a GetUserSkillsById(id) something like that.
//            // using the repository
//
//            // right now every user is being given the same set of skills and seek skills
//            // this again would later be updated to retrieve by ID NOT hard coded data
//            val newUserList = usersList.mapIndexed { index, user ->
//
//                user.copy(
//                    userSkills = DataSource().loadUserSkillsId(index + 1),
//                    userSeeksSkills = DataSource().loadUserSeeksSkillsId(index + 1)
//                )
//
//            }
//
//            _usersUiState.value = UsersUiState.Success(
//                users = newUserList,
//            )
//        } catch (exception: Exception) {
//            _usersUiState.value = UsersUiState.Error
//        }
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

//    fun getUserById() {
//        _usersUiState.value = UsersUiState.Loading
//
//        try {
//            // this is later where
//
//            // load dummy data user with pre-loaded list of skills
//            val user = DataSource().loadUserId1()
//
//            _usersUiState.value = UsersUiState.Success(
//                user = user
//            )
//        } catch (exception: Exception) {
//            _usersUiState.value = UsersUiState.Error
//        }
//    }



}