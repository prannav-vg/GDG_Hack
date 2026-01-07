package com.example.gdg_hack.ui.theme
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gdg_hack.*

@Composable
fun AppItem(
    app: AppInfo,
    runtimeCameraRisk: Boolean
) {
    val baseScore = calculateRiskScore(app)
    val finalScore = if (runtimeCameraRisk) baseScore + 5 else baseScore
    val level = getRiskLevel(finalScore)
    val over = detectOverPermissions(app)
    var showDialog by remember { mutableStateOf(false) }
    var selectedPermission by remember { mutableStateOf("") }

    val color = when (level) {
        "DANGEROUS" -> DangerRed
        "WARNING" -> WarningOrange
        else -> SafeGreen
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

    Column(Modifier.padding(12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(app.appName, fontWeight = FontWeight.Bold)
                Surface(
                    color = color.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = level,
                        color = color,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

            Text("Risk Score: $finalScore", style = MaterialTheme.typography.bodySmall)

            if (runtimeCameraRisk) {
                Spacer(Modifier.height(4.dp))
                Text(
                    "LIVE: Camera access detected",
                    color = Color.Red,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            if (over.isNotEmpty()) {
                Spacer(Modifier.height(6.dp))
                over.forEach { perm ->
                    Text(
                        text = "• $perm",
                        color = Color.Red,
                        modifier = Modifier.clickable {
                            selectedPermission = perm
                            showDialog = true
                        }
                    )
                }

            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Permission Risk") },
                text = {
                    Text(
                        when (selectedPermission) {
                            android.Manifest.permission.CAMERA ->
                                "Camera access can capture photos or videos without your knowledge."

                            android.Manifest.permission.RECORD_AUDIO ->
                                "Microphone access can record conversations silently."

                            android.Manifest.permission.ACCESS_FINE_LOCATION ->
                                "Location access can track your movements in real time."

                            else ->
                                "This permission may expose sensitive personal data."
                        }
                    )
                }
            )
        }

    }
}
