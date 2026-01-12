package com.example.gdg_hack.ui.alerts

data class Incident(
    val message: String,
    val severity: Severity,
    val timestamp: String
)

enum class Severity {
    WARNING,
    CRITICAL
}
