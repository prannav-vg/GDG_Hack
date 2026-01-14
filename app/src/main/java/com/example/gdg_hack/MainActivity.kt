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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.gdg_hack.ui.ShadowTopBar
import com.example.gdg_hack.ui.navigation.BottomNavigationBar
import com.example.gdg_hack.ui.theme.ThemeMode

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
            var themeMode by rememberSaveable {
                mutableStateOf(ThemeMode.DARK)
            }

            var showBottomBar by rememberSaveable { mutableStateOf(true) }

            ShadowDataTheme(themeMode = themeMode) {
                Box(modifier = Modifier.fillMaxSize()) {

                    // 🌳 Wood background ONLY for light mode
                    Image(
                        painter = painterResource(
                            id = when (themeMode) {
                                ThemeMode.LIGHT -> R.drawable.wood_bg
                                ThemeMode.DARK -> R.drawable.dark_bg
                                ThemeMode.GREEN -> R.drawable.green_bg
                            }
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )


                    // 🔑 Request runtime permissions ONCE
                    LaunchedEffect(Unit) {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }

                    val navController = rememberNavController()

                    // 🔐 Enforce Usage Access
                    if (!hasUsageAccess(this@MainActivity)) {
                        UsageAccessGate()
                    } else {
                        Scaffold(
                            containerColor = Color.Transparent,
                            topBar = {
                                ShadowTopBar(themeMode = themeMode)
                            },
                            bottomBar = {
                                AnimatedVisibility(visible = showBottomBar) {
                                    BottomNavigationBar(navController, themeMode)
                                }
                            }
                        ) { padding ->

                            AppNavHost(
                                navController = navController,
                                themeMode = themeMode,
                                onThemeChange = { mode -> themeMode = mode },
                                onBottomBarVisibilityChange = { show -> showBottomBar = show },
                                modifier = Modifier.padding(padding)
                            )

                        }


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
        Text(
            text = "Usage Access Required",
            color = MaterialTheme.colorScheme.onBackground
        )

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
