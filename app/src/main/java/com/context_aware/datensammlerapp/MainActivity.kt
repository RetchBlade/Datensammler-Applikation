package com.context_aware.datensammlerapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.context_aware.datensammlerapp.viewmodel.SensorConfigViewModel
import com.context_aware.datensammlerapp.viewmodel.SensorLiveDataViewModel

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private val sensorLiveDataViewModel: SensorLiveDataViewModel by viewModels()

    private val configViewModel: SensorConfigViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav =
            findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNav)
        bottomNav.setupWithNavController(navController)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        // Beobachte Konfigurationsänderungen
        configViewModel.useAccelerometer.observe(this) { updateSensorListener() }
        configViewModel.useGyroscope.observe(this) { updateSensorListener() }
        configViewModel.samplingRate.observe(this) { updateSensorListener() }

    }

    private fun updateSensorListener() {
        sensorManager.unregisterListener(this)

        val rate = when (configViewModel.samplingRate.value) {
            "FASTEST" -> SensorManager.SENSOR_DELAY_FASTEST
            "GAME" -> SensorManager.SENSOR_DELAY_GAME
            "UI" -> SensorManager.SENSOR_DELAY_UI
            else -> SensorManager.SENSOR_DELAY_NORMAL
        }

        if (configViewModel.useAccelerometer.value == true) {
            accelerometer?.also {
                sensorManager.registerListener(this, it, rate)
            }
        }

        if (configViewModel.useGyroscope.value == true) {
            gyroscope?.also {
                sensorManager.registerListener(this, it, rate)
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (configViewModel.useAccelerometer.value != true && configViewModel.useGyroscope.value != true) return

        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            when (it.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    if (configViewModel.useAccelerometer.value == true)
                        sensorLiveDataViewModel.updateAccelerometer(x, y, z)
                }
                Sensor.TYPE_GYROSCOPE -> {
                    if (configViewModel.useGyroscope.value == true)
                        sensorLiveDataViewModel.updateGyroscope(x, y, z)
                }
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
