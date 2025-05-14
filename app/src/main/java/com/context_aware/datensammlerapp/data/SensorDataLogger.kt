package com.context_aware.datensammlerapp.data

import android.content.Context
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class SensorDataLogger(context: Context) {

    private val timeFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
    private val baseDir: File = context.getExternalFilesDir(null) ?: context.filesDir

    private val accelFile = File(baseDir, "accel_${timeFormat.format(Date())}.csv")
    private val gyroFile = File(baseDir, "gyro_${timeFormat.format(Date())}.csv")

    private val accelWriter = FileWriter(accelFile, true)
    private val gyroWriter = FileWriter(gyroFile, true)

    init {
        accelWriter.write("timestamp,x,y,z\n")
        gyroWriter.write("timestamp,x,y,z\n")
    }

    fun logAccelerometer(x: Float, y: Float, z: Float) {
        val timestamp = System.currentTimeMillis()
        accelWriter.write(String.format("%d,%.4f,%.4f,%.4f\n", timestamp, x, y, z))
        accelWriter.flush()
    }

    fun logGyroscope(x: Float, y: Float, z: Float) {
        val timestamp = System.currentTimeMillis()
        gyroWriter.write(String.format("%d,%.4f,%.4f,%.4f\n", timestamp, x, y, z))
        gyroWriter.flush()
    }

    fun close() {
        accelWriter.close()
        gyroWriter.close()
    }
}
