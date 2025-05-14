package com.context_aware.datensammlerapp.data

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

object CSVExporter {

    private val timestamp: String
        get() = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())

    private fun getDownloadDir(): File {
        val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Datensammler")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun exportAccel(context: Context): File {
        val file = File(getDownloadDir(), "accel_${timestamp}.csv")
        FileWriter(file).use { writer ->
            writer.write("timestamp,x,y,z\n")
                for (entry in SensorDataCollector.accelData) {
                    writer.write("${entry.timestamp},${entry.x},${entry.y},${entry.z}\n")
                }
        }
        return file
    }

    fun exportGyro(context: Context): File {
        val file = File(getDownloadDir(), "gyro_${timestamp}.csv")
        FileWriter(file).use { writer ->
            writer.write("timestamp,x,y,z\n")
                for (entry in SensorDataCollector.gyroData) {
                    writer.write("${entry.timestamp},${entry.x},${entry.y},${entry.z}\n")
                }
        }
        return file
    }
}
