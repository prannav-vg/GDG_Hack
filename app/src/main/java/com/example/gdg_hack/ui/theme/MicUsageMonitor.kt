package com.example.gdg_hack

import android.content.Context
import android.media.AudioManager
import android.util.Log
import android.widget.Toast
import android.os.Handler
import android.os.Looper

class MicPoller(private val micMonitor: MicUsageMonitor) {

    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            micMonitor.checkMicUsage()
            handler.postDelayed(this, 3000)
        }
    }

    fun start() = handler.post(runnable)
    fun stop() = handler.removeCallbacks(runnable)
}


class MicUsageMonitor(private val context: Context) {

    private val audioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun checkMicUsage() {
        val activeMics = audioManager.activeRecordingConfigurations

        if (activeMics.isNotEmpty()) {
            notifyUser("🎤 Microphone is being used")
        }
    }

    private fun notifyUser(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        Log.d("MIC_MONITOR", message)
    }
}
