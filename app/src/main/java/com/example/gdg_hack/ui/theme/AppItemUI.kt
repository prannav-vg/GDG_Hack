package com.example.gdg_hack
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gdg_hack.ui.theme.CardBackground
import com.example.gdg_hack.ui.theme.DangerRed
import com.example.gdg_hack.ui.theme.SafeGreen
import com.example.gdg_hack.ui.theme.WarningOrange


@Composable
fun AppItemUI(
    app: AppInfo,
    runtimeCameraRisk: Boolean,
    runtimeMicRisk: Boolean
) {
    // ---------- AI SETUP ----------
    val context = LocalContext.current
    val predictor = remember { RiskPredictor(context) }

    val aiPrediction = predictor.predict(
        buildFeatures(app, runtimeCameraRisk, runtimeMicRisk)
    )

    val aiRiskLabel = when (aiPrediction) {
        0 -> "SAFE"
        1 -> "SUSPICIOUS"
        else -> "DANGEROUS"
    }

    // ---------- RULE-BASED LOGIC ----------
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

    // ---------- UI ----------
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {

            // App name + rule risk
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

            // AI risk
            Text(
                text = "AI Risk: $aiRiskLabel",
                color = when (aiRiskLabel) {
                    "SAFE" -> Color.Green
                    "SUSPICIOUS" -> Color(0xFFFF9800)
                    else -> Color.Red
                },
                style = MaterialTheme.typography.bodySmall
            )

            Text("Risk Score: $finalScore", style = MaterialTheme.typography.bodySmall)

            // Sensitive permissions
            app.permissions.forEach { permission ->
                if (
                    permission.contains("RECORD_AUDIO") ||
                    permission.contains("READ_CONTACTS") ||
                    permission.contains("ACCESS_FINE_LOCATION") ||
                    permission.contains("READ_SMS")
                ) {
                    Text(
                        text = "⚠ ${permission.substringAfterLast('.')}",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }

            Text(
                "Permissions: ${app.permissions.size}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )

            // Runtime camera
            if (runtimeCameraRisk) {
                Spacer(Modifier.height(4.dp))
                Text(
                    "LIVE: Camera access detected",
                    color = Color.Red,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            // Over-permission dialog
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
    }

    // ---------- ALERT DIALOG ----------
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
