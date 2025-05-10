package com.context_aware.datensammlerapp

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private var collecting = false

    private lateinit var accChart: LineChart
    private lateinit var gyroChart: LineChart
    private lateinit var extraChart: LineChart

    private lateinit var accX: LineDataSet
    private lateinit var accY: LineDataSet
    private lateinit var accZ: LineDataSet
    private lateinit var gyroX: LineDataSet
    private lateinit var gyroY: LineDataSet
    private lateinit var gyroZ: LineDataSet

    private lateinit var accLineData: LineData
    private lateinit var gyroLineData: LineData

    private var time = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        accChart = findViewById(R.id.acc_chart)
        gyroChart = findViewById(R.id.gyro_chart)
        extraChart = findViewById(R.id.extra_chart)

        accX = LineDataSet(mutableListOf(), "Acc X (Rot)").apply { color = Color.RED; setDrawCircles(false) }
        accY = LineDataSet(mutableListOf(), "Acc Y (Grün)").apply { color = Color.GREEN; setDrawCircles(false) }
        accZ = LineDataSet(mutableListOf(), "Acc Z (Blau)").apply { color = Color.BLUE; setDrawCircles(false) }

        gyroX = LineDataSet(mutableListOf(), "Gyro X (Orange)").apply { color = Color.rgb(255,165,0); setDrawCircles(false) }
        gyroY = LineDataSet(mutableListOf(), "Gyro Y (Cyan)").apply { color = Color.CYAN; setDrawCircles(false) }
        gyroZ = LineDataSet(mutableListOf(), "Gyro Z (Magenta)").apply { color = Color.MAGENTA; setDrawCircles(false) }

        accLineData = LineData(accX, accY, accZ)
        gyroLineData = LineData(gyroX, gyroY, gyroZ)

        accChart.data = accLineData
        accChart.description.text = "Accelerometer (m/s²)"
        accChart.legend.form = Legend.LegendForm.LINE

        gyroChart.data = gyroLineData
        gyroChart.description.text = "Gyroskop (rad/s)"
        gyroChart.legend.form = Legend.LegendForm.LINE

        extraChart.description.text = "Zusätzliche Daten (z. B. Kontext)"

        findViewById<Button>(R.id.btnStart).setOnClickListener { collecting = true }
        findViewById<Button>(R.id.btnStop).setOnClickListener { collecting = false }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
        gyroscope?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (!collecting) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                accX.addEntry(Entry(time, x))
                accY.addEntry(Entry(time, y))
                accZ.addEntry(Entry(time, z))
                accLineData.notifyDataChanged()
                accChart.notifyDataSetChanged()
                accChart.invalidate()
            }
            Sensor.TYPE_GYROSCOPE -> {
                gyroX.addEntry(Entry(time, x))
                gyroY.addEntry(Entry(time, y))
                gyroZ.addEntry(Entry(time, z))
                gyroLineData.notifyDataChanged()
                gyroChart.notifyDataSetChanged()
                gyroChart.invalidate()
            }
        }

        time += 0.1f
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
