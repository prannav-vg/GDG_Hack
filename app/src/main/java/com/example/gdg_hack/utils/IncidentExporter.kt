package com.example.gdg_hack.utils

import android.content.Context
import android.os.Environment
import com.example.gdg_hack.ui.alerts.Incident
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

object IncidentExporter {

    fun exportToCsv(
        context: Context,
        incidents: List<Incident>
    ): File {

        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())

        val fileName = "ShadowData_Report_$timeStamp.csv"

        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            fileName
        )

        FileWriter(file).use { writer ->
            writer.append("Message,Severity,Timestamp\n")

            incidents.forEach { incident ->
                writer.append(
                    "\"${incident.message}\"," +
                            "${incident.severity}," +
                            "\"${incident.timestamp}\"\n"
                )
            }
        }

        return file
    }
}
