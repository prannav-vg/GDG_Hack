package com.example.gdg_hack.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LightBlueAccent,          // buttons, highlights
    secondary = LightBlueAccent,
    background = Color.Transparent,     // image visible
    surface = DarkBlueSurface,           // screens
    surfaceVariant = DarkBlueCard,       // cards
    onPrimary = DarkTextWhite,
    onSecondary = DarkTextWhite,
    onBackground = DarkTextWhite,
    onSurface = DarkTextWhite

)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6D4C41),      // Deep wood brown
    onPrimary = Color(0xFFFFF3E0),    // Cream text

    secondary = Color(0xFF8D6E63),
    onSecondary = Color(0xFFFFF3E0),

    tertiary = Color(0xFFA1887F),

    background = Color.Transparent,  // IMPORTANT
    onBackground = Color(0xFF3E2723),

    surface = Color(0xFF4E342E),      // Brown cards
    onSurface = Color(0xFFFFE0B2),

    error = Color(0xFFD32F2F),
    onError = Color.White
)



@Composable
fun ShadowDataTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
