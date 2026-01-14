package com.example.gdg_hack.ui.alerts

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gdg_hack.utils.IncidentExporter
import com.example.gdg_hack.utils.IncidentPdfExporter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AlertsScreen(onBack: () -> Unit) {

    val context = LocalContext.current
    val incidents = remember { IncidentStore.getHighRisk() }

    LaunchedEffect(Unit) {
        IncidentStore.log(
            Incident(
                message = "Test incident: Microphone accessed during Safety Mode",
                severity = Severity.CRITICAL,
                timestamp = SimpleDateFormat(
                    "dd MMM yyyy, HH:mm",
                    Locale.getDefault()
                ).format(Date())
            )
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {

        // 🔹 EXPORT BUTTONS
        item {
            Button(
                onClick = {
                    val file = IncidentExporter.exportToCsv(context, incidents)
                    Toast.makeText(
                        context,
                        "Report exported: ${file.name}",
                        Toast.LENGTH_LONG
                    ).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text("📤 Export Security Report (CSV)")
            }
        }

        item {
            Button(
                onClick = {
                    val file = IncidentPdfExporter.exportToPdf(context, incidents)
                    Toast.makeText(
                        context,
                        "PDF exported: ${file.name}",
                        Toast.LENGTH_LONG
                    ).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("📄 Export Security Report (PDF)")
            }
        }

        // 🔹 EMPTY STATE
        if (incidents.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No high-risk incidents detected")
                }
            }
        } else {

            // 🔹 INCIDENT LIST
            items(incidents) { incident ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            if (incident.severity == Severity.CRITICAL)
                                MaterialTheme.colorScheme.errorContainer
                            else
                                MaterialTheme.colorScheme.surface
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
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.primary
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

