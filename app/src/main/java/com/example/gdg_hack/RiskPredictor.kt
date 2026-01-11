

package com.example.gdg_hack

import android.content.Context
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class RiskPredictor(context: Context) {

    private var interpreter: Interpreter? = null

    init {
        interpreter = try {
            val modelBuffer = loadModelFile(context)
            Interpreter(modelBuffer)
        } catch (e: Exception) {
            Log.e("RiskPredictor", "ML model not found, using fallback", e)
            null
        }
    }

    private fun loadModelFile(context: Context): ByteBuffer {
        val assetFileDescriptor = context.assets.openFd("risk_model.tflite")
        val inputStream = assetFileDescriptor.createInputStream()
        val modelBytes = inputStream.readBytes()

        return ByteBuffer.allocateDirect(modelBytes.size).apply {
            order(ByteOrder.nativeOrder())
            put(modelBytes)
            rewind()
        }
    }

    fun predict(features: FloatArray): Int {

        // 🔁 FALLBACK MODE (NO MODEL)
        if (interpreter == null) {
            val sensitiveCount = features[2]
            val runtimeCamera = features[6]
            val runtimeMic = features[7]

            return when {
                runtimeCamera == 1f && sensitiveCount >= 3 -> 2 // DANGEROUS
                runtimeMic == 1f -> 1                           // SUSPICIOUS
                else -> 0                                       // SAFE
            }
        }

        // 🤖 AI MODE
        val inputBuffer = ByteBuffer
            .allocateDirect(4 * features.size)
            .order(ByteOrder.nativeOrder())

        features.forEach { inputBuffer.putFloat(it) }
        inputBuffer.rewind()

        val output = Array(1) { FloatArray(3) }
        interpreter!!.run(inputBuffer, output)

        return output[0].indices.maxBy { output[0][it] } ?: 0
    }
}
