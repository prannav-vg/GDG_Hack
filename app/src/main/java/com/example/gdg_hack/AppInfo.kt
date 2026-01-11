package com.example.gdg_hack

data class AppInfo(
    val appName: String,
    val packageName: String,
    val permissions: List<String>,
    val categoryId: Int // REQUIRED FOR AI MODEL
)
