package com.example.skillswapapp.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillswapapp.data.repository.CategoryRepository
import com.example.skillswapapp.data.repository.UserRepository
import com.example.skillswapapp.dummyData.DataSource
import com.example.skillswapapp.state.HomeUiState
import com.example.skillswapapp.state.UsersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UsersViewModel (
    private val userRepository: UserRepository
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
                userRepository.getAllUsersStream().map { UsersUiState.Success(users = it) }
                    .collect { uiState ->
                        Log.d("UserList123", "Users: ${uiState.users}")
                        _usersUiState.value = uiState
                    }


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