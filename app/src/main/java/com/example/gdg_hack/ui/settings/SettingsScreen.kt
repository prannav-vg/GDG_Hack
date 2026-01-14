package com.example.gdg_hack.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    darkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    onAboutClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {

        item {
            Text(
                text = "Preferences",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dark Mode",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Switch(
                    checked = darkMode,
                    onCheckedChange = { onDarkModeToggle(it) }
                )
            }
        }

        item { Spacer(Modifier.height(24.dp)) }

        item {
            Text(
                text = "AI Risk Analysis",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Text(
                text = "Uses on-device AI to predict risk level",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        item { Spacer(Modifier.height(24.dp)) }

        item {
            Text(
                text = "Privacy",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            Text(
                text = "No data leaves your device",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        item { Spacer(Modifier.height(32.dp)) }

        item {
            Divider()
        }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            TextButton(onClick = onAboutClick) {
                Text(
                    text = "About ShadowData",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

