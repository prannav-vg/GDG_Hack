package com.example.gdg_hack.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
@Composable
fun LiveRiskBanner(
    runtimeCameraRisk: Boolean,
    womenSafetyMode: Boolean
) {
    val (bgColor, text) = when {
        runtimeCameraRisk && womenSafetyMode ->
            Pair(Color(0xFFFFCDD2), "🚨 Camera Active – Women Safety Alert")

        runtimeCameraRisk ->
            Pair(Color(0xFFFFE0B2), "⚠ Camera Access Detected")

        else ->
            Pair(Color(0xFFE8F5E9), "✅ No Active Privacy Threats")
    }

    val animatedColor by animateColorAsState(
        targetValue = bgColor,
        label = "RiskBannerColor"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = animatedColor
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp),
            fontWeight = FontWeight.Bold
        )
    }
}
