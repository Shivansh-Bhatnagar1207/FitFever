package com.example.vitalizeme.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.vitalizeme.R

class StepCounterService : Service(), SensorEventListener {


    private var initialStep = -1

    companion object {
        var callback: stepCallback? = null
        fun restStep(){
            instance?.initialStep = -1
        }

        private var instance : StepCounterService? = null
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("StepCounterService", "Service started")

        val sensorManager: SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL)
            Log.d("StepCounterService", "Step counter sensor registered")

            val notification = NotificationCompat
                .Builder(this, "Channel_ID")
                .setContentTitle("Step Counter Active")
                .setContentText("Tracking your Steps")
                .setSmallIcon(R.drawable.walk)
                .setOngoing(true)
                .build()

            startForeground(1, notification)
        } else {
            Toast.makeText(this, "No Sensors Found on device", Toast.LENGTH_SHORT).show()
        }



        return START_STICKY
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    object stepRepo{
        val stepsLiveData = MutableLiveData<Int>()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val steps = event.values[0].toInt()
            Log.d("StepCounterService", "Steps detected: $steps")


            if (initialStep == -1) {
                initialStep = steps
            }
            val currStep = steps - initialStep
            stepRepo.stepsLiveData.postValue(currStep)
        }


    }

    object Subscribe {
        fun register(callbackImplement: stepCallback) {
            callback = callbackImplement
        }

        fun unregister() {
            callback = null
            Log.d("StepCounterService", "Service Unregistered")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(this)
        stopForeground(true)
        instance = null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }
}