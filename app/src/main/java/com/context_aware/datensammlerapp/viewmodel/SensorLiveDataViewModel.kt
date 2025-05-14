package com.context_aware.datensammlerapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SensorLiveDataViewModel : ViewModel() {

    private val _accelX = MutableLiveData<Float>()
    private val _accelY = MutableLiveData<Float>()
    private val _accelZ = MutableLiveData<Float>()

    private val _gyroX = MutableLiveData<Float>()
    private val _gyroY = MutableLiveData<Float>()
    private val _gyroZ = MutableLiveData<Float>()

    val accelX: LiveData<Float> = _accelX
    val accelY: LiveData<Float> = _accelY
    val accelZ: LiveData<Float> = _accelZ

    val gyroX: LiveData<Float> = _gyroX
    val gyroY: LiveData<Float> = _gyroY
    val gyroZ: LiveData<Float> = _gyroZ

    fun updateAccelerometer(x: Float, y: Float, z: Float) {
        _accelX.value = x
        _accelY.value = y
        _accelZ.value = z
    }

    fun updateGyroscope(x: Float, y: Float, z: Float) {
        _gyroX.value = x
        _gyroY.value = y
        _gyroZ.value = z
    }
}
