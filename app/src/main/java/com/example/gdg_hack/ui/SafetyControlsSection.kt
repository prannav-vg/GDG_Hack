package com.example.gdg_hack.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SafetyControlsSection(
    womenSafetyMode: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (womenSafetyMode)
                Color(0xFFFFEBEE)
            else
                Color(0xFFF5F5F5)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("👩 Women Safety Mode", fontWeight = FontWeight.Bold)
                Text(
                    "High sensitivity privacy monitoring",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Switch(
                checked = womenSafetyMode,
                onCheckedChange = onToggle
            )
        }
    }
}
