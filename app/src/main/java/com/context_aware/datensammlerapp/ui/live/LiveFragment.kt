package com.context_aware.datensammlerapp.ui.live

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.context_aware.datensammlerapp.R
import com.context_aware.datensammlerapp.viewmodel.SensorLiveDataViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class LiveFragment : Fragment() {

    private val viewModel: SensorLiveDataViewModel by activityViewModels()

    private lateinit var textAccel: TextView
    private lateinit var textGyro: TextView
    private lateinit var chart: LineChart
    private lateinit var dataSet: LineDataSet
    private lateinit var lineData: LineData
    private var time = 0f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_live, container, false)

        textAccel = view.findViewById(R.id.text_accel)
        textGyro = view.findViewById(R.id.text_gyro)
        chart = view.findViewById(R.id.chart_accel_x)

        dataSet = LineDataSet(mutableListOf(), "Accel X").apply {
            setDrawCircles(false)
            lineWidth = 2f
        }

        lineData = LineData(dataSet)
        chart.data = lineData
        chart.description.text = "Live Accel X"
        chart.legend.form = Legend.LegendForm.LINE
        chart.setTouchEnabled(true)
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(false)

        viewModel.accelX.observe(viewLifecycleOwner) { x ->
            val y = viewModel.accelY.value ?: 0f
            val z = viewModel.accelZ.value ?: 0f

            textAccel.text = """📈 Accelerometer
-------------------
X: ${"%.2f".format(x)}
Y: ${"%.2f".format(y)}
Z: ${"%.2f".format(z)}"""

            dataSet.addEntry(Entry(time, x))
            lineData.notifyDataChanged()
            chart.notifyDataSetChanged()
            chart.invalidate()
            chart.description.apply {
                text = "Live Accel X"
                textColor = Color.WHITE
            }
            chart.legend.textColor = Color.WHITE
            chart.axisLeft.textColor = Color.WHITE
            chart.axisRight.textColor = Color.WHITE
            chart.xAxis.textColor = Color.WHITE
            time += 0.1f

            if (dataSet.entryCount > 100) {
                dataSet.removeFirst()
            }

            chart.setVisibleXRangeMaximum(50f)
            chart.moveViewToX(time)
        }

        viewModel.gyroX.observe(viewLifecycleOwner) { x ->
            val y = viewModel.gyroY.value ?: 0f
            val z = viewModel.gyroZ.value ?: 0f

            textGyro.text = """🧭 Gyroskop
-------------------
X: ${"%.2f".format(x)}
Y: ${"%.2f".format(y)}
Z: ${"%.2f".format(z)}"""
        }

        return view
    }
}
