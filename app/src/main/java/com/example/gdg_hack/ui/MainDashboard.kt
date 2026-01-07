
package com.example.gdg_hack.ui

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

import com.example.gdg_hack.InAppCameraPreview
import com.example.gdg_hack.getInstalledApps
import com.example.gdg_hack.ui.theme.AppItem
import com.example.gdg_hack.ui.theme.ScreenBackground


@Composable
fun MainDashboard() {

    val context = LocalContext.current
    val apps = remember { getInstalledApps(context) }
    var womenSafetyMode by remember { mutableStateOf(false) }
    var showCamera by remember { mutableStateOf(false) }
    var runtimeCameraRisk by remember { mutableStateOf(false) }

    BackHandler(enabled = showCamera) {
        showCamera = false
        runtimeCameraRisk = false
    }

    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(ScreenBackground)
    )
    {

        // 🔹 TOP BAR (NOW USED)
        ShadowTopBar()

        // 🔹 LIVE SECURITY STATUS (ABOVE CAMERA BUTTON)
        SecurityStatusCard(runtimeCameraRisk)

        LiveRiskBanner(
            runtimeCameraRisk = runtimeCameraRisk,
            womenSafetyMode = womenSafetyMode
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "👩 Women Safety Mode",
                fontWeight = FontWeight.Bold
            )
            Switch(
                checked = womenSafetyMode,
                onCheckedChange = { womenSafetyMode = it }
            )
        }

        // 🔹 CAMERA ACTION BUTTON
        Button(
            onClick = { showCamera = true },
            enabled = !showCamera,
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2575FC)
            )
        ) {
            Text("📷 Open In-App Camera", color = Color.Companion.White)
        }

        // 🔹 CAMERA PREVIEW
        if (showCamera) {

            TextButton(
                onClick = {
                    showCamera = false
                    runtimeCameraRisk = false
                },
                modifier = Modifier.Companion.padding(8.dp)
            ) {
                Text("Close Camera")
            }

            InAppCameraPreview {
                runtimeCameraRisk = true
            }
        }

        // 🔹 APP LIST
        LazyColumn {
            items(apps) { app ->
                AppItem(
                    app = app,
                    runtimeCameraRisk = runtimeCameraRisk
                )
            }
        }
    }
}