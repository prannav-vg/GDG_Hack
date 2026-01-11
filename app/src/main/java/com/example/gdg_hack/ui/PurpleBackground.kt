package com.example.gdg_hack.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ShiningPurpleBackground(
    content: @Composable () -> Unit
) {
    val transition = rememberInfiniteTransition(label = "purple_bg")

    val shift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradient_shift"
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF6A11CB),
            Color(0xFF8E2DE2),
            Color(0xFF2575FC)
        ),
        start = androidx.compose.ui.geometry.Offset(0f, shift),
        end = androidx.compose.ui.geometry.Offset(shift, 1000f)
    )

    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ) {
        content()
    }
}
