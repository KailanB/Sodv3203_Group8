package com.example.skillswapapp.model


import com.example.skillswapapp.data.entities.Skill
import com.example.skillswapapp.data.relations.UserWithoutSecureInfo


data class UserWithSkills(
    val user : UserWithoutSecureInfo,

    val skills: List<Skill>,
    val seeksSkills: List<Skill>
)

