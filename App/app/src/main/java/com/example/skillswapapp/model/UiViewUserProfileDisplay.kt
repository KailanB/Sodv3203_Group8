package com.example.skillswapapp.model

import com.example.skillswapapp.data.entities.Location
import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo

data class UiViewUserProfileDisplay(

    val user : UserWithoutSecureInfo,

    val skills: List<UserSkillDetails>,
    val seeksSkills: List<UserSeeksSkillsDetails>
)
