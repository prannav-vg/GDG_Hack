package com.example.gdg_hack.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.gdg_hack.AppItemUI
import com.example.gdg_hack.getInstalledApps
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    onBack: () -> Unit,
    onAppClick: (String) -> Unit
) {val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val listState = rememberLazyListState()

    val context = LocalContext.current
    val allApps = remember {
        getInstalledApps(context).take(15)
    }

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filteredApps = allApps.filter {
        it.appName.contains(searchQuery.text, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {

            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    placeholder = {
                        Text(
                            "Search apps",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 1f),
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

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

