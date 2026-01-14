package com.example.gdg_hack.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gdg_hack.ui.theme.ThemeMode
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShadowTopBar(
    themeMode: ThemeMode,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (themeMode) {
        ThemeMode.LIGHT -> MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
        ThemeMode.DARK -> Color.Black
        ThemeMode.GREEN -> Color(0xFF0A1F14)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .padding(vertical = 20.dp, horizontal = 16.dp)
    ) {
        Column {
            Text(
                text = "ShadowData",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Privacy & Permission Monitor",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp
            )
        }
    }
}

