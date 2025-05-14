package com.context_aware.datensammlerapp.ui.live

import android.R.attr.y
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.context_aware.datensammlerapp.R
import com.context_aware.datensammlerapp.viewmodel.SensorLiveDataViewModel

class LiveFragment : Fragment() {

    private val viewModel: SensorLiveDataViewModel by activityViewModels()

    private lateinit var textAccel: TextView
    private lateinit var textGyro: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_live, container, false)

        textAccel = view.findViewById(R.id.text_accel)
        textGyro = view.findViewById(R.id.text_gyro)

        viewModel.accelX.observe(viewLifecycleOwner) { x ->
            val y = viewModel.accelY.value ?: 0f
            val z = viewModel.accelZ.value ?: 0f
            val ax = String.format("%.2f", x)
            val ay = String.format("%.2f", y)
            val az = String.format("%.2f", z)
            textAccel.text = "Accelerometer:\nX: $ax\nY: $ay\nZ: $az"
        }


        viewModel.gyroX.observe(viewLifecycleOwner) { x ->
            val y = viewModel.gyroY.value ?: 0f
            val z = viewModel.gyroZ.value ?: 0f
            val gx = String.format("%.2f", x)
            val gy = String.format("%.2f", y)
            val gz = String.format("%.2f", z)
            textGyro.text = "Gyroskop:\nX: $gx\nY: $gy\nZ: $gz"
        }


        return view
    }
}
