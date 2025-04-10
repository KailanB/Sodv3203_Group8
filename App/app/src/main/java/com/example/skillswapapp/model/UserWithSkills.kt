package com.example.skillswapapp.model


import com.example.skillswapapp.data.entities.Skill
import com.example.skillswapapp.data.relations.UserSeeksSkillsDetails
import com.example.skillswapapp.data.relations.UserSkillDetails
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo


data class UserWithSkills(
    val user : UserWithoutSecureInfo,

    val skills: List<UserSkillDetails>,
    val seeksSkills: List<UserSeeksSkillsDetails>
)

