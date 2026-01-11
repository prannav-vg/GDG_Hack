package com.example.gdg_hack.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrivacyTimeline() {

    val events = PrivacyTimelineState.events

    if (events.isEmpty()) return

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            "🕒 Privacy Timeline",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        events.take(5).forEach { event ->
            Text(
                "• ${event.timestamp} – ${event.message}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
