package com.example.gdg_hack

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.gdg_hack.ui.navigation.AppNavHost
import com.example.gdg_hack.ui.theme.ShadowDataTheme
import android.Manifest
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import com.example.gdg_hack.ui.navigation.BottomNavigationBar

class MainActivity : ComponentActivity() {

    private val micPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                Toast.makeText(
                    this,
                    "Microphone permission is required for live monitoring",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (!granted) {
                Toast.makeText(
                    this,
                    "Camera permission is required for live monitoring",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShadowDataTheme {

                // 🔑 Request runtime permissions ONCE
                LaunchedEffect(Unit) {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }

                val navController = rememberNavController()

                // 🔐 Enforce Usage Access
                if (!hasUsageAccess(this)) {
                    UsageAccessGate()
                } else {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }
                    ) { padding ->
                        AppNavHost(
                            navController = navController,
                            modifier = Modifier.padding(padding)
                        )
                    }

                }
            }
        }
    }
}

fun hasUsageAccess(context: Context): Boolean {
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode = appOps.checkOpNoThrow(
        AppOpsManager.OPSTR_GET_USAGE_STATS,
        android.os.Process.myUid(),
        context.packageName
    )
    return mode == AppOpsManager.MODE_ALLOWED
}

@Composable
fun UsageAccessGate() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Usage Access Required")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            context.startActivity(
                Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            )
        }) {
            Text("Grant Usage Access")
        }
    }
}
