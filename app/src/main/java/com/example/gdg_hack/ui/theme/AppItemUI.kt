package com.example.gdg_hack.ui.theme
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gdg_hack.*

@Composable
fun AppItem(
    app: AppInfo,
    runtimeCameraRisk: Boolean
) {
    val baseScore = calculateRiskScore(app)
    val finalScore = if (runtimeCameraRisk) baseScore + 5 else baseScore
    val level = getRiskLevel(finalScore)
    val over = detectOverPermissions(app)

    val color = when (level) {
        "DANGEROUS" -> DangerRed
        "WARNING" -> WarningOrange
        else -> SafeGreen
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

    Column(Modifier.padding(12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(app.appName, fontWeight = FontWeight.Bold)
                Surface(
                    color = color.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = level,
                        color = color,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

            Text("Risk Score: $finalScore", style = MaterialTheme.typography.bodySmall)

            if (runtimeCameraRisk) {
                Spacer(Modifier.height(4.dp))
                Text(
                    "LIVE: Camera access detected",
                    color = Color.Red,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            if (over.isNotEmpty()) {
                Spacer(Modifier.height(6.dp))
                over.forEach {
                    Text("• $it", color = Color.Red, fontSize = 12.sp)
                }
            }
        }
    }
}
