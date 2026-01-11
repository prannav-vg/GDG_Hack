package com.example.gdg_hack.ui


import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gdg_hack.*
import com.example.gdg_hack.ui.theme.ScreenBackground


@Composable
fun MainDashboard() {

    val context = LocalContext.current
    val apps = remember { getInstalledApps(context) }

    var runtimeMicRisk by remember { mutableStateOf(false) }
    val micMonitor = remember { MicUsageMonitor1() }

    var womenSafetyMode by remember { mutableStateOf(false) }
    var showCamera by remember { mutableStateOf(false) }
    var runtimeCameraRisk by remember { mutableStateOf(false) }

    // Handle back press when camera is open
    BackHandler(enabled = showCamera) {
        showCamera = false
        runtimeCameraRisk = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
    ) {

        // 🔹 TOP BAR
        ShadowTopBar()

        Spacer(Modifier.height(8.dp))

        // 🔹 SECURITY STATUS CARD
        SecurityStatusCard(runtimeCameraRisk)

        Spacer(Modifier.height(8.dp))

        // 🔹 LIVE RISK BANNER
        LiveRiskBanner(
            runtimeCameraRisk = runtimeCameraRisk,
            womenSafetyMode = womenSafetyMode
        )

        Spacer(Modifier.height(8.dp))

        // 🔹 PRIVACY TIMELINE
        PrivacyTimeline()

        Spacer(Modifier.height(8.dp))

        // 🔹 WOMEN SAFETY MODE
        SafetyControlsSection(
            womenSafetyMode = womenSafetyMode,
            onToggle = {
                womenSafetyMode = it
                if (it) {
                    PrivacyTimelineState.log("Women Safety Mode enabled")
                }
            }
        )

        Spacer(Modifier.height(12.dp))

        // 🎙️ MICROPHONE TEST BUTTON
        Button(
            onClick = {
                micMonitor.startMonitoring {
                    runtimeMicRisk = true
                    PrivacyTimelineState.log("Microphone accessed")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2))
        ) {
            Text("🎙️ Test Microphone Access", color = Color.White)
        }

        TextButton(onClick = {
            micMonitor.stopMonitoring()
            runtimeMicRisk = false
        }) {
            Text("Stop Microphone")
        }

        Spacer(Modifier.height(8.dp))

        // 📷 CAMERA BUTTON
        Button(
            onClick = { showCamera = true },
            enabled = !showCamera,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEF5350)
            ),
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            Text(
                text = "📷 Open In-App Camera",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        // 📷 CAMERA PREVIEW
        if (showCamera) {
            TextButton(
                onClick = {
                    showCamera = false
                    runtimeCameraRisk = false
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Close Camera")
            }

            InAppCameraPreview {
                runtimeCameraRisk = true
                PrivacyTimelineState.log("Camera accessed")
            }
        }

        Spacer(Modifier.height(8.dp))

        // 🔐 SENSITIVE ACCESS PANEL (LIVE STATE)
        SensitiveAccessPanel(
            camera = runtimeCameraRisk,
            mic = runtimeMicRisk,
            contacts = apps.any { app ->
                app.permissions.any { it.contains("READ_CONTACTS") }
            },
            location = apps.any { app ->
                app.permissions.any { it.contains("LOCATION") }
            }
        )

        // 📱 APP LIST
        LazyColumn(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            items(apps) { app ->
                AppItemUI(
                    app = app,
                    runtimeCameraRisk = runtimeCameraRisk,
                    runtimeMicRisk = runtimeMicRisk
                )

            }
        }
    }
}
