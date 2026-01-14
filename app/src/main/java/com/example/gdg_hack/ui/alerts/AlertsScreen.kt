package com.example.gdg_hack.ui.alerts

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->

        val context = LocalContext.current
        val incidents = IncidentStore.getHighRisk()
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

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            // 🔹 EXPORT BUTTON (NOW VISIBLE)
            Button(
                onClick = {
                    val file = IncidentExporter.exportToCsv(
                        context,
                        incidents
                    )

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
            Button(
                onClick = {
                    val file = IncidentPdfExporter.exportToPdf(
                        context,
                        incidents
                    )

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

            // 🔹 INCIDENT LIST / EMPTY STATE
            if (incidents.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No high-risk incidents detected")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
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
                                        MaterialTheme.colorScheme.surfaceVariant
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
    }

}
