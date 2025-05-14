package com.context_aware.datensammlerapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SensorConfigViewModel : ViewModel() {

    private val _useAccelerometer = MutableLiveData(false)
    val useAccelerometer: LiveData<Boolean> = _useAccelerometer

    private val _useGyroscope = MutableLiveData(false)
    val useGyroscope: LiveData<Boolean> = _useGyroscope

    private val _samplingRate = MutableLiveData("NORMAL")
    val samplingRate: LiveData<String> = _samplingRate

    private val _collecting = MutableLiveData(false)
    val collecting: LiveData<Boolean> = _collecting

    fun setUseAccelerometer(enabled: Boolean) {
        _useAccelerometer.postValue(enabled)
    }

    fun setUseGyroscope(enabled: Boolean) {
        _useGyroscope.postValue(enabled)
    }

    fun setSamplingRate(rate: String) {
        _samplingRate.postValue(rate)
    }

    fun setCollecting(state: Boolean) {
        _collecting.postValue(state)
    }
}