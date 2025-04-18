package com.example.skillswapapp.model

import com.example.skillswapapp.data.entities.Location
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.relations.UserFriendList
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails

class UiUserProfileDisplay(
    val user: User,

    val skills: List<UserSkillDetails>,
    val seeksSkills: List<UserSeeksSkillsDetails>,
    val location: Location,
)