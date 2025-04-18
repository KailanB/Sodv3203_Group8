package com.example.skillswapapp.view

import android.provider.Contacts.Intents.UI
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.skillswapapp.AppViewModelProvider
import com.example.skillswapapp.data.relations.CategoryWithSkills
import com.example.skillswapapp.model.Location
import com.example.skillswapapp.model.UiDisplaySkill
import com.example.skillswapapp.model.UiUserProfileDisplay
import com.example.skillswapapp.state.UserEntryCategorySkillsUiState
import com.example.skillswapapp.ui.components.ErrorScreen
import com.example.skillswapapp.ui.components.LoadingScreen
import com.example.skillswapapp.ui.components.SkillCard
import com.example.skillswapapp.ui.theme.SkillSwapAppTheme
import com.example.skillswapapp.ui.theme.tertiaryLight
import com.example.skillswapapp.viewModel.LocationDetails
import com.example.skillswapapp.viewModel.UserDetails
import com.example.skillswapapp.viewModel.UserEntryViewModel
import com.example.skillswapapp.viewModel.UserUiState
import kotlinx.coroutines.launch

@Composable
fun UserEntryScreen(
    navController: NavHostController,
    currentUser: UiUserProfileDisplay?,
    isEditing: Boolean,
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val viewModel: UserEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categorySkillsUiState by viewModel.categorySkillsUiState.collectAsState()

    LaunchedEffect(currentUser) {
        viewModel.initializeForEdit(
            currentUser
        )
    }

    when (val state = categorySkillsUiState) {
        is UserEntryCategorySkillsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is UserEntryCategorySkillsUiState.Success -> {

            UserEntryBody(
                viewModel = viewModel,
                isEditing = isEditing,
                userUiState = viewModel.userUiState,
                onUserValueChange = viewModel::updateUiState,
                onLocationValueChange = viewModel::updateLocationUiState,
                onMySkillChange = viewModel::updateMySkillUiState,
                onSkillSeekingChange = viewModel::updateMySeekingSkillUiState,
                categoryWithUserList = state.categories,
                onSaveClick = {
                    coroutineScope.launch {
                        val accountCreated = viewModel.saveUser()
                        if (accountCreated && !isEditing) {
                            navController.navigate("LoginScreen")
                        } else {
                            // display an error about account creation
                        }
                        // navigateBack()
                    }
                },
                onAddNewSkillClick = {
                    coroutineScope.launch {
                        viewModel.addNewUserSkill()
                    }
                },
                onAddNewSeekingSkillClick = {
                    coroutineScope.launch {
                        viewModel.addNewUserSeekingSkill()
                    }
                }
            )


        }
        is UserEntryCategorySkillsUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }


}


@Composable
fun UserEntryBody(
    viewModel: UserEntryViewModel,
    isEditing: Boolean,
    userUiState: UserUiState,
    onUserValueChange: (UserDetails) -> Unit,
    onLocationValueChange: (LocationDetails) -> Unit,
    onMySkillChange: (UiDisplaySkill) -> Unit,
    onSkillSeekingChange: (UiDisplaySkill) -> Unit,
    categoryWithUserList: List<CategoryWithSkills>,
    onSaveClick: () -> Unit,
    onAddNewSkillClick: () -> Unit,
    onAddNewSeekingSkillClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
           .background(Color.White)
//            .background(Color(0xFFF5F5F5))
//            .background(Color(0xFFE0F2F1))

            .padding(12.dp)
    ){


        UserDetailsInput(
            userDetails = userUiState.userDetails,
            modifier =modifier,
            onUserValueChange = onUserValueChange,
            enabled = true
        )
        LocationDetailsInput(
            locationDetails = userUiState.locationDetails,
            modifier = modifier,
            onLocationValueChange = onLocationValueChange,

            )
        if(isEditing)
        {
            SkillsDisplay(
                skillListTitle = "My Skills",
                skillsList = userUiState.mySkills,
                onSkillRemove = { skill -> viewModel.deleteMySkill(skill) }
            )
            SkillInput(
                skillSection = "Add New Skills",
                skillDetails = userUiState.mySkillDetails,
                categoriesWithSkills = categoryWithUserList,
                onAddNewSkillClick = onAddNewSkillClick,
                onSkillChange = onMySkillChange
            )
            SkillsDisplay(
                skillListTitle = "Skills I am Seeking",
                skillsList = userUiState.skillSeeking,
                onSkillRemove = { skill -> viewModel.deleteSkillSeeking(skill) }
            )
            SkillInput(
                skillSection = "Add New Skills to Seek",
                skillDetails = userUiState.skillSeekingDetails,
                categoriesWithSkills = categoryWithUserList,
                onAddNewSkillClick = onAddNewSeekingSkillClick,
                onSkillChange = onSkillSeekingChange
            )
        }

        Button(
            onClick = onSaveClick,
            enabled = userUiState.isEntryValid,
            colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        )
        ){
            Text(text = "Save")
        }

    }
}


@Composable
fun UserDetailsInput(
    userDetails: UserDetails,
    modifier: Modifier = Modifier,
    onUserValueChange: (UserDetails) -> Unit = {},
    enabled: Boolean = true
) {

    OutlinedTextField(
        value = userDetails.name,
        onValueChange = {onUserValueChange(userDetails.copy(name = it))},
        label = { Text(text = "Name", color = Color.Black) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(color = tertiaryLight),

    )

    OutlinedTextField(
        value = userDetails.email,
        onValueChange = {onUserValueChange(userDetails.copy(email = it))},
        label = { Text(text = "Email", color = Color.Black) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)
    )

    OutlinedTextField(
        value = userDetails.password,
        onValueChange = {onUserValueChange(userDetails.copy(password = it))},
        label = { Text(text = "Password", color = Color.Black) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)
    )

    OutlinedTextField(
        value = userDetails.reTypePassword,
        onValueChange = {onUserValueChange(userDetails.copy(reTypePassword = it))},
        label = { Text(text = "re-type Password", color = Color.Black) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)
    )

    OutlinedTextField(
        value = userDetails.profileIntro ?: "",
        onValueChange = {onUserValueChange(userDetails.copy(profileIntro = it))},
        label = { Text(text = "Profile Intro", color = Color.Black) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)
    )

    OutlinedTextField(
        value = userDetails.description ?: "",
        onValueChange = {onUserValueChange(userDetails.copy(description = it))},
        label = { Text(text = "Description", color = Color.Black) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)
    )

    OutlinedTextField(
        value = userDetails.preferences ?: "",
        onValueChange = {onUserValueChange(userDetails.copy(description = it))},
        label = { Text(text = "Preferences", color = Color.Black) },
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)
    )
}

@Composable
fun LocationDetailsInput(
    locationDetails: LocationDetails,
    modifier: Modifier = Modifier,
    onLocationValueChange: (LocationDetails) -> Unit = {}
) {

    OutlinedTextField(
        value = locationDetails.city,
        onValueChange = {onLocationValueChange(locationDetails.copy(city = it))},
        label = { Text(text = "City") },
        enabled = true,
        singleLine = true,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.surface)
    )

    var expanded by remember { mutableStateOf(false) }
    val provinces = listOf( "AB", "BC", "MB", "NB", "NL", "NS", "ON", "PE", "QC", "SK", "NT", "NU", "YT")
    var selectedProvinceIndex by remember { mutableIntStateOf(0)}

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.width(150.dp)
    ){
        Text(
            text = "Province",
            color = Color.Black

            )
        Box(){
            OutlinedButton(onClick = { expanded = true }) {
                Text(
                    text = provinces[selectedProvinceIndex],
                    color = MaterialTheme.colorScheme.surface
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                provinces.forEachIndexed{index, province ->
                    DropdownMenuItem(
                        onClick = {
                            selectedProvinceIndex = index
                            expanded = false
                            onLocationValueChange(locationDetails.copy(province = province))
                        },
                        text = { Text(text = province) }

                    )
                }
            }
        }


    }



}

@Composable
fun SkillsDisplay(
    // viewModel: UserEntryViewModel,
    onSkillRemove: (UiDisplaySkill) -> Unit,
    skillListTitle: String,
    skillsList: List<UiDisplaySkill>
) {
    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    )
    {
        Text(
            text = skillListTitle,
            color = Color.Black,
            style = MaterialTheme.typography.titleLarge
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(4.dp)
                .fillMaxWidth()
        ) {
            skillsList.forEach { skill ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.width(300.dp)
                )
                {
                    Text(
                        text = skill.skillName,
                        color = MaterialTheme.colorScheme.surface
                    )
                    Button(
                        onClick = {
                            onSkillRemove(skill)
                        },
                        modifier = Modifier.padding(start = 20.dp)
                    ){
                        Text(
                            text = "Delete Skill"
                        )
                    }
                }

            }

        }
    }



}

@Composable
fun SkillInput(
    skillSection: String,
    skillDetails: UiDisplaySkill,
    categoriesWithSkills: List<CategoryWithSkills>,
    onAddNewSkillClick: () -> Unit,
    onSkillChange: (UiDisplaySkill) -> Unit,
) {
    var categoryExpanded by remember { mutableStateOf(false) }
    var skillExpanded by remember { mutableStateOf(false) }
    var skillSelected by remember { mutableStateOf(false) }

    var selectedCategoryIndex by remember { mutableStateOf(0)}
    var selectedSkillIndex by remember { mutableStateOf(0)}
    var selectedSkillId by remember {mutableStateOf(0)}


    Text(
        text = skillSection,
        color = Color.Black,
        style = MaterialTheme.typography.titleLarge
    )
    Card(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
                .padding(10.dp)
                .fillMaxWidth()

        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "Skill Category"
                )
                Box() {
                    OutlinedButton(onClick = { categoryExpanded = true }) {
                        Text(categoriesWithSkills[selectedCategoryIndex].category.category,
                            color = MaterialTheme.colorScheme.surface)
                    }

                    DropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        categoriesWithSkills.forEachIndexed{index, categoryWithSkills ->
                            DropdownMenuItem(
                                onClick = {
                                    if(index != selectedCategoryIndex)
                                    {
                                        selectedSkillIndex = 0
                                        skillSelected = false
                                        selectedCategoryIndex = index
                                    }
                                    categoryExpanded = false
                                },
                                text = {Text(categoryWithSkills.category.category)}
                            )
                        }

                    }
                }


            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "Skill"
                )
                Box(){
                    OutlinedButton(onClick = { skillExpanded = true }) {
                        Text(
                            text = if(skillSelected){
                                categoriesWithSkills[selectedCategoryIndex].skills[selectedSkillIndex].skill_name
                            }
                            else
                            {
                                "Select Skill"
                            },
                            color = MaterialTheme.colorScheme.surface
                        )

                    }

                    DropdownMenu(
                        expanded = skillExpanded,
                        onDismissRequest = { skillExpanded = false }
                    ){
                        categoriesWithSkills[selectedCategoryIndex].skills.forEachIndexed{ index, skill ->
                            DropdownMenuItem(
                                onClick = {
                                    onSkillChange(skillDetails.copy(skillId = skill.skill_id))
                                    selectedSkillIndex = index
                                    skillSelected = true
                                    skillExpanded = false
                                },
                                text = {Text(skill.skill_name)}
                            )
                        }
                    }
                }

            }
            OutlinedTextField(
                value = skillDetails.description ?: "",
                onValueChange = {onSkillChange(skillDetails.copy(description = it))},
                label = { Text(text = "Description") },
                enabled = true,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if(skillSelected)
                    {
                        onAddNewSkillClick()
                    }
                },
                enabled = skillSelected
            ) {
                Text("Add Skill")
            }
        }
    }









}


//@Preview(showBackground = true)
//@Composable
//private fun UserEntryScreenPreview() {
//    SkillSwapAppTheme {
//        UserEntryBody(userUiState = UserUiState(
//            UserDetails(
//                name = "test",
//                email = "Email",
//                password = "123456",
//                reTypePassword = "1231252",
//                locationId = 0
//            )
//        ),
//            onSaveClick = {},
//            onUserValueChange = {},
//            onLocationValueChange = {}
//            )
//    }
//}