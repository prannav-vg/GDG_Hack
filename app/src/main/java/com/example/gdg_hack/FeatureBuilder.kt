package com.example.gdg_hack


fun buildFeatures(
    app: AppInfo,
    runtimeCamera: Boolean,
    runtimeMic: Boolean
): FloatArray {

    val sensitivePermissions = listOf(
        "CAMERA",
        "RECORD_AUDIO",
        "ACCESS_FINE_LOCATION",
        "READ_CONTACTS"
    )

    return floatArrayOf(
        app.categoryId.toFloat(),
        app.permissions.size.toFloat(),
        app.permissions.count { perm ->
            sensitivePermissions.any { perm.contains(it) }
        }.toFloat(),
        if (app.permissions.any { it.contains("CAMERA") }) 1f else 0f,
        if (app.permissions.any { it.contains("RECORD_AUDIO") }) 1f else 0f,
        if (app.permissions.any { it.contains("LOCATION") }) 1f else 0f,
        if (runtimeCamera) 1f else 0f,
        if (runtimeMic) 1f else 0f
    )
}
