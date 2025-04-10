package com.example.skillswapapp.model

import com.example.skillswapapp.data.entities.User
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo

class UiUserDisplay (
    val user : UserWithoutSecureInfo,

    val skills: List<UiDisplaySkill>,
    val seeksSkills: List<UiDisplaySkill>
)
