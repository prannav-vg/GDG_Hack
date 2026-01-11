package com.example.gdg_hack

import android.Manifest
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.annotation.RequiresPermission

class MicUsageMonitor1 {

    private var recorder: AudioRecord? = null

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun startMonitoring(onMicDetected: () -> Unit) {
        val bufferSize = AudioRecord.getMinBufferSize(
            44100,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        recorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            44100,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        recorder?.startRecording()
        onMicDetected()
    }

    fun stopMonitoring() {
        recorder?.stop()
        recorder?.release()
        recorder = null
    }
}
