package com.example.gdg_hack
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
    runtimeMicRisk: Boolean,
    onClick: (() -> Unit)? = null
){
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
    // ---------- FINAL RISK (AI HAS PRIORITY) ----------
    val finalRiskLabel = aiRiskLabel

    val finalRiskColor = when (finalRiskLabel) {
        "SAFE" -> SafeGreen
        "SUSPICIOUS" -> WarningOrange
        else -> DangerRed
    }

    // ---------- RULE-BASED LOGIC ----------
    val baseScore = calculateRiskScore(app)
    val finalScore = if (runtimeCameraRisk) baseScore + 5 else baseScore
    val level = getRiskLevel(finalScore)
    val over = detectOverPermissions(app)
    var showDetails by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedPermission by remember { mutableStateOf("") }

    // ---------- UI ----------
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .let {
                if (onClick != null) it.clickable { onClick() } else it
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {

            // App name + rule risk
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (app.appName.length > 18)
                        app.appName.take(18) + "..."
                    else
                        app.appName,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    color = finalRiskColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(50)
                ){
                    Text(
                        text = finalRiskLabel,
                        color = finalRiskColor,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // AI risk
            Text(
                text = "AI Risk: $aiRiskLabel",
                color = finalRiskColor,
                style = MaterialTheme.typography.bodySmall
            )


            Text("Risk Score: $finalScore", style = MaterialTheme.typography.bodySmall,color = MaterialTheme.colorScheme.onSurface)

            TextButton(
                onClick = { showDetails = !showDetails }
            ) {
                Text(
                    text = if (showDetails) "Hide Details ▲" else "Show Details ▼",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            AnimatedVisibility(
                visible = showDetails,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {

                    // 🔐 Permissions count
                    Text(
                        "Permissions: ${app.permissions.size}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    // ⚠️ Sensitive permissions
                    // 🔐 User-friendly permission explanations
                    val readablePermissions = app.permissions
                        .map { PermissionMapper.getFriendlyName(it) } // 🔑 map FIRST
                        .distinct()                                   // remove duplicates
                        .sorted()

                    Text(
                        "What this app can do:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    Spacer(Modifier.height(4.dp))

                    readablePermissions.forEach { friendly ->
                        Text(
                            text = "• $friendly",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.sp
                        )
                    }



                    // 📷 Runtime camera
                    if (runtimeCameraRisk) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "LIVE: Camera access detected",
                            color = DangerRed,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    // 🎙 Runtime mic
                    if (runtimeMicRisk) {
                        Text(
                            "LIVE: Microphone access detected",
                            color = WarningOrange,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    // 🚨 Over-permission warnings (USER FRIENDLY)
                    if (over.isNotEmpty()) {
                        Spacer(Modifier.height(6.dp))

                        Text(
                            "High-risk permissions:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        over
                            .map { PermissionMapper.getFriendlyName(it) }
                            .distinct()
                            .forEach { friendly ->
                                Text(
                                    text = "• $friendly",
                                    color = DangerRed,
                                    fontSize = 13.sp
                                )
                            }
                    }

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
