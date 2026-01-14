package com.example.gdg_hack.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gdg_hack.ui.theme.ThemeMode

@Composable
fun SettingsScreen(
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    onBack: () -> Unit,
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

        // 🔹 THEME SELECTION
        item {
            Text(
                text = "Theme",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item { Spacer(Modifier.height(8.dp)) }

        ThemeMode.values().forEach { mode ->
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onThemeChange(mode) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = themeMode == mode,
                        onClick = { onThemeChange(mode) }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = mode.name.lowercase()
                            .replaceFirstChar { it.uppercase() },
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        item { Spacer(Modifier.height(24.dp)) }

        // 🔹 AI INFO
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
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        item { Spacer(Modifier.height(24.dp)) }

        // 🔹 PRIVACY
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
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        item { Spacer(Modifier.height(32.dp)) }

        item { Divider() }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            TextButton(onClick = onAboutClick) {
                Text(
                    text = "About ShadowData",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
