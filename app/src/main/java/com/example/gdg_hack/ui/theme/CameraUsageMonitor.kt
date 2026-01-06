package com.example.gdg_hack

import android.content.Context
import android.hardware.camera2.CameraManager
import android.util.Log
import android.widget.Toast

class CameraUsageMonitor(context: Context) {

    private val cameraManager =
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    private val callback = object : CameraManager.AvailabilityCallback() {

        override fun onCameraUnavailable(cameraId: String) {
            Log.d("CAMERA_MONITOR", "Camera in use")
            Toast.makeText(
                context,
                "📷 Camera is currently in use",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onCameraAvailable(cameraId: String) {
            Log.d("CAMERA_MONITOR", "Camera released")
        }
    }

    fun start() {
        cameraManager.registerAvailabilityCallback(callback, null)
    }

    fun stop() {
        cameraManager.unregisterAvailabilityCallback(callback)
    }
}
