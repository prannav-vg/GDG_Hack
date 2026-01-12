package com.example.gdg_hack

object PermissionMapper {

    fun getFriendlyName(permission: String): String {
        return when {

            // 🎥 Camera
            permission.contains("CAMERA") ->
                "Camera access (can take photos or videos)"

            // 🎙 Microphone
            permission.contains("RECORD_AUDIO") ||
                    permission.contains("MICROPHONE") ->
                "Microphone access (can listen to audio)"

            // 📍 Location
            permission.contains("ACCESS_FINE_LOCATION") ||
                    permission.contains("ACCESS_COARSE_LOCATION") ->
                "Location access (can track your location)"

            // 👥 Contacts
            permission.contains("READ_CONTACTS") ||
                    permission.contains("WRITE_CONTACTS") ->
                "Contacts access (can read your contacts)"

            // 📩 SMS & Calls
            permission.contains("READ_SMS") ->
                "SMS access (can read text messages)"

            permission.contains("READ_CALL_LOG") ->
                "Call history access (can view call logs)"

            permission.contains("READ_PHONE_STATE") ->
                "Phone status access (can read device status)"

            // 🌐 Network
            permission.contains("INTERNET") ->
                "Internet access (can send or receive data online)"

            permission.contains("ACCESS_NETWORK_STATE") ||
                    permission.contains("ACCESS_WIFI_STATE") ->
                "Network information access"

            // 🗂 Storage / Media
            permission.contains("READ_MEDIA") ||
                    permission.contains("READ_EXTERNAL_STORAGE") ||
                    permission.contains("WRITE_EXTERNAL_STORAGE") ->
                "Photos and media access"

            permission.contains("MANAGE_DOCUMENTS") ->
                "File management access"

            // ⏱ Background behavior
            permission.contains("FOREGROUND_SERVICE") ->
                "Runs continuously in the background"

            permission.contains("WAKE_LOCK") ->
                "Keeps the device awake in background"

            permission.contains("RECEIVE_BOOT_COMPLETED") ->
                "Starts automatically when the phone is turned on"

            permission.contains("DYNAMIC_RECEIVER") ->
                "Listens for system events in the background"

            // 📦 App visibility
            permission.contains("QUERY_ALL_PACKAGES") ->
                "Can see the list of installed apps"

            permission.contains("PACKAGE_USAGE_STATS") ->
                "Monitors how apps are used"

            // 📢 Notifications & ads
            permission.contains("VIBRATE") ->
                "Controls vibration"

            permission.contains("AD_ID") ->
                "Uses advertising identifier"

            permission.contains("C2D_MESSAGE") ->
                "Receives cloud notifications"

            // 📡 Hardware
            permission.contains("NFC") ->
                "Uses Near Field Communication (NFC)"

            // 🧩 Fallback
            else ->
                "Uses additional system features"
        }
    }
}

