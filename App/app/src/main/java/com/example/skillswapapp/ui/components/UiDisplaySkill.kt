package com.example.skillswapapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skillswapapp.model.UiDisplaySkill

@Composable
fun SkillCard(
    skill: UiDisplaySkill
)
{
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = skill.skillName,
            // color = Color.White,
            // modifier = Modifier.align(Alignment.Center)
        )
    }
}