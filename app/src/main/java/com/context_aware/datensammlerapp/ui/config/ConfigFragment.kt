package com.context_aware.datensammlerapp.ui.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.context_aware.datensammlerapp.R
import com.context_aware.datensammlerapp.viewmodel.SensorConfigViewModel

class ConfigFragment : Fragment() {

    private val viewModel: SensorConfigViewModel by activityViewModels()

    private lateinit var switchAccelerometer: Switch
    private lateinit var switchGyroscope: Switch
    private lateinit var spinnerSamplingRate: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_config, container, false)

        switchAccelerometer = view.findViewById(R.id.switch_accelerometer)
        switchGyroscope = view.findViewById(R.id.switch_gyroscope)
        spinnerSamplingRate = view.findViewById(R.id.spinner_sampling_rate)

        val rates = listOf("FASTEST", "GAME", "UI", "NORMAL")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, rates)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSamplingRate.adapter = adapter

        // Initialwerte setzen
        viewModel.useAccelerometer.observe(viewLifecycleOwner) {
            switchAccelerometer.isChecked = it
        }
        viewModel.useGyroscope.observe(viewLifecycleOwner) { switchGyroscope.isChecked = it }
        viewModel.samplingRate.observe(viewLifecycleOwner) {
            val index = rates.indexOf(it)
            if (index >= 0) spinnerSamplingRate.setSelection(index)
        }

        switchAccelerometer.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setUseAccelerometer(isChecked)
        }

        switchGyroscope.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setUseGyroscope(isChecked)
        }

        spinnerSamplingRate.setOnItemSelectedListener { _, _, position, _ ->
            viewModel.setSamplingRate(rates[position])
        }

        return view
    }

    private fun Spinner.setOnItemSelectedListener(onChange: (parent: Spinner, view: View?, position: Int, id: Long) -> Unit) {
        this.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                onChange(this@setOnItemSelectedListener, view, position, id)
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }
    }
}
