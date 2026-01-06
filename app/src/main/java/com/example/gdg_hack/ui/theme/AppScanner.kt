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

        if ((appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0) continue

        apps.add(
            AppInfo(
                appName = appInfo.loadLabel(pm).toString(),
                packageName = pkg.packageName,
                permissions = pkg.requestedPermissions?.toList() ?: emptyList()
            )
        )
    }
    return apps
}
