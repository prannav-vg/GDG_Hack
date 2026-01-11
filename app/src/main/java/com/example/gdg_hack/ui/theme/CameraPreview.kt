package com.example.gdg_hack

import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.gdg_hack.ui.PrivacyTimelineState

@Composable
fun InAppCameraPreview(
    onCameraDetected: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var detected by remember { mutableStateOf(false) }

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    DisposableEffect(Unit) {
        onDispose {
            // 🔴 RELEASE CAMERA WHEN LEAVING SCREEN
            cameraProviderFuture.get().unbindAll()
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { ctx ->
            val previewView = PreviewView(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview
                )

                if (!detected) {
                    detected = true
                    onCameraDetected()
                    PrivacyTimelineState.log("Camera accessed")
                }

            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}

