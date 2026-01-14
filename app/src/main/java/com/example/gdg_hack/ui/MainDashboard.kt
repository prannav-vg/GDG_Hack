package com.example.gdg_hack.ui


import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import com.example.gdg_hack.ui.safety.WomenSafetyState
import com.example.gdg_hack.ui.safety.disableWomenSafety
import com.example.gdg_hack.ui.safety.enableWomenSafety
import android.provider.Settings
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboard(
    onViewApps: () -> Unit,
    onBottomBarVisibilityChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    // 🔥 Combine real + demo apps
    val apps = remember {
        getInstalledApps(context)
            .sortedWith(compareBy<AppInfo> { app ->
                when {
                    app.packageName == context.packageName -> 0
                    app.packageName == "com.google.android.youtube" ||
                            app.appName.contains("YouTube", ignoreCase = true) -> 1
                    app.appName.contains("calendar", ignoreCase = true) -> 2
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

    BackHandler(enabled = showCamera) {
        showCamera = false
        runtimeCameraRisk = false
    }

    val listState = rememberLazyListState()



    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ){

            item { Spacer(Modifier.height(8.dp)) }

            // 🔹 SECURITY STATUS
            item { SecurityStatusCard(runtimeCameraRisk) }

            item { Spacer(Modifier.height(12.dp)) }

            // 🔹 STATS
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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

            item { Spacer(Modifier.height(12.dp)) }

            // 🔹 WOMEN SAFETY MODE
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Privacy Mode",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Locks camera & microphone system-wide",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                        Switch(
                            checked = WomenSafetyState.enabled,
                            onCheckedChange = {
                                if (it) enableWomenSafety(context)
                                else disableWomenSafety()
                            }
                        )
                    }
                }
            }

            item { Spacer(Modifier.height(16.dp)) }

            // 🔹 LIVE TESTS
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            text = "Live Permission Tests",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = {
                                micMonitor.startMonitoring {
                                    micActiveApp = context.packageName
                                    PrivacyTimelineState.log("Microphone accessed")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Test Microphone")
                        }

                        TextButton(
                            onClick = {
                                micMonitor.stopMonitoring()
                                micActiveApp = null
                            }
                        ) {
                            Text("Stop Microphone",color = MaterialTheme.colorScheme.onSurface)
                        }

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = { showCamera = true },
                            enabled = !showCamera,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Open Camera")
                        }
                    }
                }
            }

            // 📷 CAMERA PREVIEW
            if (showCamera) {
                item {
                    TextButton(
                        onClick = {
                            showCamera = false
                            runtimeCameraRisk = false
                        }
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

            item { Spacer(Modifier.height(12.dp)) }

            // 🔐 SENSITIVE ACCESS PANEL
            item {
                SensitiveAccessPanel(
                    camera = runtimeCameraRisk,
                    mic = micActiveApp != null,
                    contacts = apps.any { it.permissions.any { p -> p.contains("READ_CONTACTS") } },
                    location = apps.any { it.permissions.any { p -> p.contains("LOCATION") } }
                )
            }

            item { Spacer(Modifier.height(12.dp)) }

            // 🔹 VIEW APPS
            item {
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


    // 🚨 PRIVACY VIOLATION ALERT
    LaunchedEffect(runtimeCameraRisk, micActiveApp) {
        if (WomenSafetyState.enabled && (runtimeCameraRisk || micActiveApp != null)) {
            showPrivacyViolationDialog = true
        }
    }

    if (showPrivacyViolationDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("🚨 Privacy Lock Violation") },
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
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                title,
                fontWeight = FontWeight.Bold,color = MaterialTheme.colorScheme.onSurface)
            Text(
                value,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

