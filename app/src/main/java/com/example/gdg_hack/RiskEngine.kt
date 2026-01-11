package com.example.gdg_hack

val permissionRiskWeights = mapOf(
    "CAMERA" to 5,
    "RECORD_AUDIO" to 5,
    "MICROPHONE" to 5,
    "LOCATION" to 4,
    "CONTACTS" to 3,
    "STORAGE" to 2,
    "INTERNET" to 1,
    "READ_SMS" to 8,
    "READ_CALL_LOG" to 7
)

fun calculateRiskScore(app: AppInfo): Int {
    return app.permissions.sumOf { perm ->
        permissionRiskWeights[perm.substringAfterLast(".")] ?: 0
    }
}

fun getRiskLevel(score: Int): String =
    when {
        score >= 15 -> "DANGEROUS"
        score >= 7 -> "WARNING"
        else -> "SAFE"
    }


