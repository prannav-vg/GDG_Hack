package com.example.gdg_hack.ui.about

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About ShadowData",color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text("What ShadowData Does", fontWeight = FontWeight.Bold,color = MaterialTheme.colorScheme.onSurface)
            Text(
                "ShadowData monitors how apps access sensitive resources like camera and microphone."
            ,color = MaterialTheme.colorScheme.onPrimary)

            Spacer(Modifier.height(12.dp))

            Text("AI Risk Prediction", fontWeight = FontWeight.Bold,color = MaterialTheme.colorScheme.onSurface)
            Text(
                "An on-device AI model predicts whether an app is Safe, Suspicious, or Dangerous."
            ,color = MaterialTheme.colorScheme.onPrimary)

            Spacer(Modifier.height(12.dp))

            Text("Privacy First", fontWeight = FontWeight.Bold,color = MaterialTheme.colorScheme.onSurface)
            Text(
                "No audio, video, or personal data is recorded or sent to the cloud."
 ,color = MaterialTheme.colorScheme.onPrimary           )

            Spacer(Modifier.height(12.dp))

            Text("Explainability", fontWeight = FontWeight.Bold,color = MaterialTheme.colorScheme.onSurface)
            Text(
                "All permissions are translated into simple language so users can understand risks.",color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
