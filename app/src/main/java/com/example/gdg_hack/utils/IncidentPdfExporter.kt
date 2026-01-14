package com.example.gdg_hack.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.example.gdg_hack.ui.alerts.Incident
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object IncidentPdfExporter {

    fun exportToPdf(
        context: Context,
        incidents: List<Incident>
    ): File {

        val pdfDocument = PdfDocument()
        val paint = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(
            595, // A4 width
            842, // A4 height
            1
        ).create()

        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        var y = 40

        // Title
        paint.textSize = 18f
        paint.isFakeBoldText = true
        canvas.drawText("ShadowData – Security Incident Report", 40f, y.toFloat(), paint)

        y += 30

        paint.textSize = 12f
        paint.isFakeBoldText = false
        canvas.drawText(
            "Generated on: ${
                SimpleDateFormat(
                    "dd MMM yyyy, HH:mm",
                    Locale.getDefault()
                ).format(Date())
            }",
            40f,
            y.toFloat(),
            paint
        )

        y += 30

        if (incidents.isEmpty()) {
            canvas.drawText(
                "No high-risk incidents recorded.",
                40f,
                y.toFloat(),
                paint
            )
        } else {
            incidents.forEach { incident ->
                canvas.drawText(
                    "• ${incident.message}",
                    40f,
                    y.toFloat(),
                    paint
                )
                y += 16

                canvas.drawText(
                    "  Severity: ${incident.severity}",
                    60f,
                    y.toFloat(),
                    paint
                )
                y += 16

                canvas.drawText(
                    "  Time: ${incident.timestamp}",
                    60f,
                    y.toFloat(),
                    paint
                )
                y += 24
            }
        }

        pdfDocument.finishPage(page)

        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())

        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "ShadowData_Report_$timeStamp.pdf"
        )

        FileOutputStream(file).use {
            pdfDocument.writeTo(it)
        }

        pdfDocument.close()

        return file
    }
}
