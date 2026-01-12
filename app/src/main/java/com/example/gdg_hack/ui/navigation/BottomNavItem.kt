package com.example.gdg_hack.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.List


sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Dashboard : BottomNavItem(
        NavRoutes.Dashboard.route,
        "Home",
        Icons.Filled.Home
    )

    object Apps : BottomNavItem(
        NavRoutes.AppList.route,
        "Apps",
        Icons.Filled.List
    )

    object Alerts : BottomNavItem(
        NavRoutes.Alerts.route,
        "Alerts",
        Icons.Filled.Warning
    )

    object Settings : BottomNavItem(
        NavRoutes.Settings.route,
        "Settings",
        Icons.Filled.Settings
    )
}
