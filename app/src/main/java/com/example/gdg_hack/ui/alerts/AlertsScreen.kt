package com.example.gdg_hack.ui.alerts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(onBack: () -> Unit) {

    val incidents = remember { IncidentStore.getHighRisk() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alerts & Incidents") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        if (incidents.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No high-risk incidents detected")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(incidents) { incident ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor =
                                if (incident.severity == Severity.CRITICAL)
                                    Color(0xFFFFCDD2)
                                else
                                    Color(0xFFFFF3E0)
                        )
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(
                                incident.message,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                incident.severity.name,
                                color =
                                    if (incident.severity == Severity.CRITICAL)
                                        Color.Red
                                    else
                                        Color(0xFFFF9800)
                            )
                            Text(
                                incident.timestamp,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}
