package com.example.gdg_hack.ui


import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gdg_hack.AppInfo
import com.example.gdg_hack.InAppCameraPreview
import com.example.gdg_hack.MicUsageMonitor1
import com.example.gdg_hack.getInstalledApps
import com.example.gdg_hack.ui.navigation.BottomNavItem
import com.example.gdg_hack.ui.safety.WomenSafetyState
import com.example.gdg_hack.ui.safety.disableWomenSafety
import com.example.gdg_hack.ui.safety.enableWomenSafety
import android.provider.Settings


@Composable
fun MainDashboard(onViewApps: () -> Unit) {

    val context = LocalContext.current

// 🔥 Combine real + demo apps
    val apps = remember {
        getInstalledApps(context)
            .sortedWith(compareBy<AppInfo> { app ->
                when {
                    // 1️⃣ Your app (GDG_Hack)
                    app.packageName == context.packageName -> 0

                    // 2️⃣ YouTube
                    app.packageName == "com.google.android.youtube" ||
                            app.appName.contains("YouTube", ignoreCase = true) -> 1

                    // 3️⃣ Calendar
                    app.packageName.contains("calendar", ignoreCase = true) ||
                            app.appName.contains("Calendar", ignoreCase = true) -> 2

                    // 4️⃣ Any other app
                    else -> 3
                }
            })
            .take(15)
    }

    var micActiveApp by remember { mutableStateOf<String?>(null) }
    var showPrivacyViolationDialog by remember { mutableStateOf(false) }

    val micMonitor = remember { MicUsageMonitor1() }
    var showCamera by remember { mutableStateOf(false) }
    var runtimeCameraRisk by remember { mutableStateOf(false) }

    // Handle back press when camera is open
    BackHandler(enabled = showCamera) {
        showCamera = false
        runtimeCameraRisk = false
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ,
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {


        // 🔹 TOP BAR
        item{ ShadowTopBar() }

        item{ Spacer(Modifier.height(8.dp)) }

        // 🔹 SECURITY STATUS CARD
        item{ SecurityStatusCard(runtimeCameraRisk) }

        item{ Spacer(Modifier.height(8.dp)) }


        item{ Spacer(Modifier.height(8.dp)) }

        item {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                DashboardStat("Apps", apps.size.toString())
                DashboardStat(
                    "Camera",
                    if (WomenSafetyState.enabled) "LOCKED"
                    else if (runtimeCameraRisk) "ON"
                    else "OFF"
                )

                DashboardStat(
                    "Mic",
                    if (WomenSafetyState.enabled) "LOCKED"
                    else if (micActiveApp != null) "ON"
                    else "OFF"
                )


            }
        }
        item{ Spacer(Modifier.height(8.dp)) }

        // 🔹 WOMEN SAFETY MODE
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text = "Privacy Mode",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Locks camera & microphone system-wide",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = WomenSafetyState.enabled,
                        onCheckedChange = { enabled ->
                            if (enabled) enableWomenSafety(context)
                            else disableWomenSafety()
                        }
                    )
                }
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Text(
                        "Live Permission Tests",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = {
                            micMonitor.startMonitoring {
                                micActiveApp = context.packageName // or selected app
                                PrivacyTimelineState.log("Microphone accessed by ${micActiveApp}")
                            }

                        },

                        modifier = Modifier.fillMaxWidth()
                    ,
                    ) {
                        Text("Test Microphone")
                    }

                    TextButton(onClick = {
                        micMonitor.stopMonitoring()
                        micActiveApp = null
                    }) {
                        Text("Stop Microphone")
                    }


                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = { showCamera = true },
                        enabled = !showCamera,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )

                    ) {
                        Text("Open Camera", color = MaterialTheme.colorScheme.onError)
                    }
                }
            }
        }


        // 📷 CAMERA PREVIEW
        if (showCamera) {
            item{
                TextButton(
                    onClick = {
                        showCamera = false
                        runtimeCameraRisk = false
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Close Camera")
                }
            }
            item {
                AnimatedVisibility(
                    visible = showCamera,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    InAppCameraPreview {
                        runtimeCameraRisk = true
                        PrivacyTimelineState.log("Camera accessed")
                    }
                }
            }

        }

        item{ Spacer(Modifier.height(8.dp)) }

        // 🔐 SENSITIVE ACCESS PANEL (LIVE STATE)
        item{
            SensitiveAccessPanel(
                camera = runtimeCameraRisk,
                mic = micActiveApp != null,
                contacts = apps.any { app ->
                    app.permissions.any { it.contains("READ_CONTACTS") }
                },
                location = apps.any { app ->
                    app.permissions.any { it.contains("LOCATION") }
                }
            )

        }
       item{
            Button(
                onClick = onViewApps,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("📱 View Installed Apps")
            }
        }

    }
    LaunchedEffect(runtimeCameraRisk, micActiveApp) {
            if (WomenSafetyState.enabled &&
                (runtimeCameraRisk || micActiveApp != null)
            ) {
                PrivacyTimelineState.log(
                    "Privacy violation: Camera/Mic accessed during Women Safety Mode"
                )
                showPrivacyViolationDialog = true
            }
        }
    if (showPrivacyViolationDialog) {
        AlertDialog(
            onDismissRequest = { /* block dismiss */ },
            title = {
                Text("🚨 Privacy Lock Violation")
            },
            text = {
                Text(
                    "An application attempted to access your camera or microphone " +
                            "while Women Safety Mode is active."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPrivacyViolationDialog = false
                        context.startActivity(
                            Intent(Settings.ACTION_PRIVACY_SETTINGS)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                    }
                ) {
                    Text("Disable Camera & Mic")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showPrivacyViolationDialog = false
                        WomenSafetyState.enabled = false
                    }
                ) {
                    Text("Exit Safety Mode")
                }
            }
        )
    }

}


@Composable
fun DashboardStat(title: String, value: String) {
    Card(
        modifier = Modifier.size(width = 100.dp, height = 70.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                value,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

