package com.example.myapplication.models

import androidx.lifecycle.MutableLiveData
import com.ipleiria.estg.meicm.arcismarthome.models.Home
import com.ipleiria.estg.meicm.arcismarthome.models.User
import org.eclipse.paho.android.service.MqttAndroidClient


class AppData {

    lateinit var user: User
    var home: Home = Home(null, null, null, null)

    var notification = MutableLiveData<Int>()
    var sensorUpdate = MutableLiveData<Int>()
    lateinit var mqttClient: MqttAndroidClient

    private object HOLDER {
        val INSTANCE = AppData()
    }

    companion object {
        val instance: AppData by lazy { HOLDER.INSTANCE }
    }
}