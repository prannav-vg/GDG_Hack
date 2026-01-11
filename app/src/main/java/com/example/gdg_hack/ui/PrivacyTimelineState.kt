package com.example.gdg_hack.ui

import androidx.compose.runtime.mutableStateListOf

object PrivacyTimelineState {
    val events = mutableStateListOf<PrivacyEvent>()

    fun log(message: String) {
        val time = java.text.SimpleDateFormat(
            "hh:mm a",
            java.util.Locale.getDefault()
        ).format(java.util.Date())

        events.add(
            0,
            PrivacyEvent(message, time)
        )
    }
}
