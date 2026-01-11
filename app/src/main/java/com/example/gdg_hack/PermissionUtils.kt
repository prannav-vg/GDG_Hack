package com.example.gdg_hack

import android.Manifest

fun explainPermission(permission: String): String {
    return when (permission) {
        Manifest.permission.CAMERA ->
            "Can capture photos or videos without your knowledge."

        Manifest.permission.RECORD_AUDIO ->
            "Can listen to conversations or background sounds."

        Manifest.permission.READ_CONTACTS ->
            "Can access personal contact details."

        Manifest.permission.ACCESS_FINE_LOCATION ->
            "Can track your precise location."

        Manifest.permission.READ_SMS ->
            "Can read private messages and OTPs."

        else -> "Sensitive permission access detected."
    }
}
