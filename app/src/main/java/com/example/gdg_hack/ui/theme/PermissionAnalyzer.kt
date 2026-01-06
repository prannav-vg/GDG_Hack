package com.example.gdg_hack

fun detectOverPermissions(app: AppInfo): List<String> {
    val name = app.appName.lowercase()
    val expected = permissionBenchmarks.entries
        .firstOrNull { name.contains(it.key) }
        ?.value ?: emptyList()

    return app.permissions
        .map { it.substringAfterLast(".") }
        .filter { it !in expected }
}
