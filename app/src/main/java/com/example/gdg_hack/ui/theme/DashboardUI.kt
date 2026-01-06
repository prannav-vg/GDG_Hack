package com.example.gdg_hack.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gdg_hack.*

@Composable
fun DashboardCard(title: String, value: String, color: Color) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .width(120.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.labelMedium)
            Text(
                value,
                color = color,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun DashboardSummary(apps: List<AppInfo>) {
    val highRisk = apps.count {
        getRiskLevel(calculateRiskScore(it)) == "DANGEROUS"
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DashboardCard("Apps", apps.size.toString(), Color.Blue)
        DashboardCard("High Risk", highRisk.toString(), Color.Red)
        DashboardCard(
            "Status",
            if (highRisk > 0) "Alert" else "Safe",
            if (highRisk > 0) Color.Red else Color.Green
        )
    }
}
