package com.example.gdg_hack.ui.navigation

sealed class NavRoutes(val route: String) {
    object Dashboard : NavRoutes("dashboard")
    object AppList : NavRoutes("app_list")
    object AppDetail : NavRoutes("app_detail/{packageName}") {
        fun createRoute(packageName: String) =
            "app_detail/$packageName"
    }
    object Alerts : NavRoutes("alerts")
    object Settings : NavRoutes("settings")
    object About : NavRoutes("about")

}
