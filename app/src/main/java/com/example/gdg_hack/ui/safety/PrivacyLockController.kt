package com.example.gdg_hack.ui.safety

import android.content.Context
import android.content.Intent
import android.provider.Settings

fun enableWomenSafety(context: Context) {
    // 1. Enable safety state
    WomenSafetyState.enabled = true

    // 2. Open system privacy controls
    val intent = Intent(Settings.ACTION_PRIVACY_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

fun disableWomenSafety() {
    WomenSafetyState.enabled = false
}
