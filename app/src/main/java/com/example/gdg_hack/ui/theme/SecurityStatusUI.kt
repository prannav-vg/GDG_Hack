package com.example.gdg_hack.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gdg_hack.ui.theme.DangerRed
import com.example.gdg_hack.ui.theme.SafeGreen

@Composable
fun SecurityStatusCard(runtimeCameraRisk: Boolean) {

    val bgColor = if (runtimeCameraRisk)
        DangerRed.copy(alpha = 0.12f)
    else
        SafeGreen.copy(alpha = 0.12f)

    val textColor = if (runtimeCameraRisk) DangerRed else SafeGreen

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                if (runtimeCameraRisk) "⚠ Live Camera Access Detected"
                else "✅ No Active Privacy Threats",
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
