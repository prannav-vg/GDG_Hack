package com.example.gdg_hack.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.gdg_hack.AppItemUI
import com.example.gdg_hack.getInstalledApps

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    onBack: () -> Unit,
    onAppClick: (String) -> Unit
) {
    val context = LocalContext.current
    val allApps = remember {
        getInstalledApps(context).take(15)
    }

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filteredApps = allApps.filter {
        it.appName.contains(searchQuery.text, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Installed Apps") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                placeholder = { Text("Search apps") }
            )

            LazyColumn {
                items(filteredApps) { app ->
                    AppItemUI(
                        app = app,
                        runtimeCameraRisk = false,
                        runtimeMicRisk = false,
                        onClick = {
                            onAppClick(app.packageName)
                        }
                    )
                }
            }
        }
    }
}
