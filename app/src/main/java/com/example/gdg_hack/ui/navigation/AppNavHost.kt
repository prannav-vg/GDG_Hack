package com.example.gdg_hack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gdg_hack.ui.*
import com.example.gdg_hack.ui.about.AboutScreen
import com.example.gdg_hack.ui.alerts.AlertsScreen
import com.example.gdg_hack.ui.settings.SettingsScreen
import com.example.gdg_hack.ui.theme.ThemeMode

@Composable
fun AppNavHost(
    navController: NavHostController,
    themeMode: ThemeMode,
    onThemeChange: (ThemeMode) -> Unit,
    onBottomBarVisibilityChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
)

 {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Dashboard.route,
        modifier = modifier
    ) {

        composable(NavRoutes.Dashboard.route) {
            MainDashboard(
                onViewApps = {
                    navController.navigate(NavRoutes.AppList.route)
                },onBottomBarVisibilityChange = onBottomBarVisibilityChange
            )
        }

        composable(NavRoutes.AppList.route) {
            AppListScreen(
                onBack = { navController.popBackStack() },
                onAppClick = { packageName ->
                    navController.navigate(
                        NavRoutes.AppDetail.createRoute(packageName)
                    )
                }
            )
        }

        composable(NavRoutes.AppDetail.route) { backStack ->
            val pkg = backStack.arguments?.getString("packageName") ?: return@composable
            AppDetailScreen(
                packageName = pkg,
                onBack = { navController.popBackStack() }
            )
        }
        composable(NavRoutes.Alerts.route) {
            AlertsScreen(onBack = { navController.popBackStack() })
        }

        composable(NavRoutes.Settings.route) {
            SettingsScreen(
                themeMode = themeMode,
                onThemeChange = onThemeChange,
                onBack = { navController.popBackStack() },
                onAboutClick = {
                    navController.navigate(NavRoutes.About.route)
                }
            )
        }


        composable(NavRoutes.About.route) {
            AboutScreen(onBack = { navController.popBackStack() })
        }

    }
}
