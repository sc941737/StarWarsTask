package com.sc941737.lib.ui_theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(Dimensions.small),
    medium = RoundedCornerShape(Dimensions.small),
    large = RoundedCornerShape(0.dp)
)

@Immutable
object Dimensions {
    val xSmall = 2.dp
    val small = 4.dp
    val medium = 8.dp
    val large = 16.dp
    val xLarge = 24.dp
    val xxLarge = 32.dp
}