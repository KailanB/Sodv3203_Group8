// ui/theme/Shape.kt
package com.example.skillswapapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small  = RoundedCornerShape(4.dp),   // for tiny chips/icons
    medium = RoundedCornerShape(8.dp),   // default button radius
    large  = RoundedCornerShape(16.dp)   // for large cards/containers
)
