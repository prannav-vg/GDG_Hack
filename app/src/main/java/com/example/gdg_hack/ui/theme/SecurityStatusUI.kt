package com.example.gdg_hack.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
@Composable
fun SecurityStatusCard(runtimeCameraRisk: Boolean) {

    val bgColor = if (runtimeCameraRisk)
        MaterialTheme.colorScheme.errorContainer
    else
        MaterialTheme.colorScheme.primaryContainer

    val textColor = if (runtimeCameraRisk)
        MaterialTheme.colorScheme.onErrorContainer
    else
        MaterialTheme.colorScheme.onPrimaryContainer

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (runtimeCameraRisk)
                    Icons.Default.Warning
                else
                    Icons.Default.CheckCircle,
                contentDescription = null,
                tint = textColor
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = if (runtimeCameraRisk)
                    "Live Privacy Threat Detected"
                else
                    "No Active Privacy Threats",
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


