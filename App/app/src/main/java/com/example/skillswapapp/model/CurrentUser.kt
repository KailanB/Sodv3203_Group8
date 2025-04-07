package com.example.skillswapapp.model

import com.example.skillswapapp.data.entities.Skill
import com.example.skillswapapp.data.entities.User

class CurrentUser (
    val user : User,

    val skills: List<Skill>,
    val seeksSkills: List<Skill>
)