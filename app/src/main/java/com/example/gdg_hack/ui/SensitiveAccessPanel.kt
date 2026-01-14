package com.example.gdg_hack.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SensitiveAccessPanel(
    camera: Boolean,
    mic: Boolean,
    contacts: Boolean,
    location: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )

    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text("🔐 Sensitive Access Overview", fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface)

            Spacer(Modifier.height(8.dp))

            AccessRow("Camera", camera)
            AccessRow("Microphone", mic)
            AccessRow("Contacts", contacts)
            AccessRow("Location", location)
        }
    }
}
@Composable
fun AccessRow(label: String, active: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label,color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
        Text(
            if (active) "ACTIVE" else "INACTIVE",
            color = if (active) Color.Red else Color(0xFF2E7D32),
            fontWeight = FontWeight.Bold
        )
    }
}

