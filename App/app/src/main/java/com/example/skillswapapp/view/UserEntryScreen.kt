package com.example.skillswapapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skillswapapp.AppViewModelProvider
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.ui.theme.SkillSwapAppTheme
import com.example.skillswapapp.viewModel.UserDetails
import com.example.skillswapapp.viewModel.UserEntryViewModel
import com.example.skillswapapp.viewModel.UserUiState
import kotlinx.coroutines.launch

@Composable
fun UserEntryScreen(
    viewModel: UserEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    currentUser: User?
){
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(currentUser) {
        viewModel.initializeForEdit(currentUser)
    }

    UserEntryBody(
        userUiState = viewModel.userUiState,
        onUserValueChange = viewModel::updateUiState,
        onSaveClick = {
            coroutineScope.launch {
                viewModel.saveUser()
                // navigateBack()
            }
        }
    )
}


@Composable
fun UserEntryBody(
    userUiState: UserUiState,
    onUserValueChange: (UserDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        UserInputForm(
            userDetails = userUiState.userDetails,
            onValueChange = onUserValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = userUiState.isEntryValid
        ){
            Text(text = "Save")
        }

    }
}

@Composable
fun UserInputForm(
    userDetails: UserDetails,
    modifier: Modifier = Modifier,
    onValueChange: (UserDetails) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){

        OutlinedTextField(
            value = userDetails.name,
            onValueChange = {onValueChange(userDetails.copy(name = it))},
            label = { Text(text = "Name") },
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = userDetails.email,
            onValueChange = {onValueChange(userDetails.copy(email = it))},
            label = { Text(text = "Email") },
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = userDetails.password,
            onValueChange = {onValueChange(userDetails.copy(password = it))},
            label = { Text(text = "Password") },
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = userDetails.reTypePassword,
            onValueChange = {onValueChange(userDetails.copy(reTypePassword = it))},
            label = { Text(text = "re-type Password") },
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = userDetails.profileIntro ?: "",
            onValueChange = {onValueChange(userDetails.copy(profileIntro = it))},
            label = { Text(text = "Profile Intro") },
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = userDetails.description ?: "",
            onValueChange = {onValueChange(userDetails.copy(description = it))},
            label = { Text(text = "Description") },
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = userDetails.preferences ?: "",
            onValueChange = {onValueChange(userDetails.copy(description = it))},
            label = { Text(text = "Preferences") },
            enabled = enabled,
            singleLine = true
        )


//        OutlinedTextField(
//            value = locationDetails.city,
//            onValueChange = {onValueChange(locationDetails.copy(city = it))},
//            label = { Text(text = "City") },
//            enabled = enabled,
//            singleLine = true
//        )

//        var expanded by remember { mutableStateOf(false) }
//        val provinces = listOf( "AB", "BC", "MB", "NB", "NL", "NS", "ON", "PE", "QC", "SK", "NT", "NU", "YT")
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            provinces.forEach { province ->
//                DropdownMenuItem(
//                    onClick = {
//                        expanded = false
//                        onValueChange(locationDetails.copy(province = province))
//                    },
//                    text = { Text(text = province) }
//                )
//            }
//
//        }



    }
}


@Preview(showBackground = true)
@Composable
private fun UserEntryScreenPreview() {
    SkillSwapAppTheme {
        UserEntryBody(userUiState = UserUiState(
            UserDetails(
                name = "test",
                email = "Email",
                password = "123456",
                reTypePassword = "1231252",
                locationId = 0
            )
        ),
            onSaveClick = {},
            onUserValueChange = {})
    }
}