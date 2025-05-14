package com.context_aware.datensammlerapp.data

data class SensorEntry(val timestamp: Long, val x: Float, val y: Float, val z: Float)

object SensorDataCollector {
    val accelData = mutableListOf<SensorEntry>()
    val gyroData = mutableListOf<SensorEntry>()

    fun addAccelerometer(x: Float, y: Float, z: Float) {
        accelData.add(SensorEntry(System.currentTimeMillis(), x, y, z))
    }

    fun addGyroscope(x: Float, y: Float, z: Float) {
        gyroData.add(SensorEntry(System.currentTimeMillis(), x, y, z))
    }

    fun clearAll() {
        accelData.clear()
        gyroData.clear()
    }
}
