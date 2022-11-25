package com.arash.altafi.samplecompass1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.Channel
import kotlin.math.roundToInt

class SensorDataManager(context: Context) : SensorEventListener {
    private val sensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    fun init() {
        sensorManager.registerListener(
            this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    val data: Channel<Float> = Channel(Channel.UNLIMITED)

    override fun onSensorChanged(event: SensorEvent) {
        val degree = event.values[0].roundToInt().toFloat()

        data.trySend(degree)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    fun cancel() {
        sensorManager.unregisterListener(this)
    }
}