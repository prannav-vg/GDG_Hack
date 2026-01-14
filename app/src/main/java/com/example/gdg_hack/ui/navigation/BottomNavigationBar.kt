package com.example.gdg_hack.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavController,
    isDarkMode: Boolean
)
 {
    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Apps,
        BottomNavItem.Alerts,
        BottomNavItem.Settings
    )

     NavigationBar(
         containerColor =
             if (isDarkMode)
                 Color.Black
             else
                 MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
     )

     {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(NavRoutes.Dashboard.route)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    )

                },
                label = {
                    Text(
                        text = item.label,
                        color = if (currentRoute == item.route)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    )

                }
            )
        }
    }
}
