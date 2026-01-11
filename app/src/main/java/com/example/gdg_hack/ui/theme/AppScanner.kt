package com.example.gdg_hack

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

fun getInstalledApps(context: Context): List<AppInfo> {
    val pm = context.packageManager
    val packages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)
    val apps = mutableListOf<AppInfo>()

    for (pkg in packages) {
        val appInfo = pkg.applicationInfo ?: continue

        // Skip system apps
        if ((appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0) continue

        val categoryId = try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                appInfo.category   // ✅ FIXED
            } else {
                0
            }
        } catch (e: Exception) {
            0
        }

        apps.add(
            AppInfo(
                appName = appInfo.loadLabel(pm).toString(),
                packageName = pkg.packageName,
                permissions = pkg.requestedPermissions?.toList() ?: emptyList(),
                categoryId = categoryId
            )
        )
    }
    return apps
}
