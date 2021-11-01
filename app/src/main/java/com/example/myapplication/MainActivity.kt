package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.wear.widget.WearableLinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.AppData
import com.example.myapplication.models.Sensor
import com.ipleiria.estg.meicm.arcismarthome.models.User
import com.ipleiria.estg.meicm.arcismarthome.server.ApiClient
import com.ipleiria.estg.meicm.arcismarthome.server.LoginRequest
import com.ipleiria.estg.meicm.arcismarthome.server.LoginResponse
import com.ipleiria.estg.meicm.arcismarthome.server.SensorValueRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : WearableActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorsList: ArrayList<Sensor>
    var adapter: SensorsViewAdapter? = null
    var listView: ListView? = null


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        listView = findViewById(R.id.listview_1)
        sensorsList = ArrayList<Sensor>()

        val loginRequest = LoginRequest()
        loginRequest.username = "smarthome"
        loginRequest.password = "meicm123"
        loginUser(loginRequest)




        (listView as ListView).onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                if (sensorsList?.get(i).value == 1.0) {
                    sensorsList?.get(i).value = 0.0
                } else {
                    sensorsList?.get(i).value = 1.0
                }
                updateSensorValue(sensorsList?.get(i))
            }

    }


    private fun loginUser(loginRequest: LoginRequest) {
        val loginResponseCall = ApiClient.service.loginUser(loginRequest)
        loginResponseCall!!.enqueue((object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        AppData.instance.user = loginResponse.key?.let {
                            User(
                                null,
                                loginRequest.username,
                                null,
                                null,
                                null,
                                "Token $it"
                            )
                        }!!
                        //loginRequest.token = loginResponse.key

                        downloadSensors()
                    }
                } else {
                    val message = "Login failed"
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.e("Request failed", "login call failed")
            }
        }))
    }

    private fun downloadSensors() {
        val sensorsGetResponseCall = ApiClient.service.getAllSensors(AppData.instance.user.token)
        sensorsGetResponseCall.enqueue((object : Callback<List<Sensor>> {
            override fun onResponse(call: Call<List<Sensor>>, response: Response<List<Sensor>>) {
                for (i in response.body()!!) {
                    sensorsList.add(Sensor(i.id, i.name, i.sensortype, i.value, i.gpio, i.room))
                }
                populateSensoresList()
            }
            override fun onFailure(call: Call<List<Sensor>>, t: Throwable) {
                sensorsList = ArrayList<Sensor>()
            }
        }))
    }

    private fun populateSensoresList() {
        adapter = SensorsViewAdapter(this, sensorsList)

        (listView as ListView).adapter = adapter
    }

    private fun updateSensorValue(sensor: Sensor) {
        val body = SensorValueRequest(sensor.id, sensor.value)
        val sensorValueUpdateCall =
            ApiClient.service.updateSensorValue(AppData.instance.user.token, body)
        sensorValueUpdateCall.enqueue((object : Callback<Void> {

            var message = "";
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                message = sensor.name + " - Updated"
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                message = sensor.name + " - Not updated"
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()

            }
        }))
    }
/*
    fun connect(context: Context) {
        val serverURI = "tcp://161.35.8.148:1883"
        AppData.instance.mqttClient = MqttAndroidClient(context, serverURI, AppData.instance.user.username)
        AppData.instance.mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d(TAG, "Receive message: ${message.toString()} from topic: $topic")

                var jsonmsg: JSONObject = JSONObject(message.toString())
                val value =  jsonmsg["value"] as String

                if (jsonmsg["action"]  == "newPhoto" && jsonmsg["user"] == AppData.instance.user.username) {
                    AppData.instance.notification.value = value.toInt()

                    if (!lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)){

                        val notifyIntent = Intent(this@MainActivity, NotificationReceiver::class.java)
                        val alarmManager = this@MainActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                        val notifyPendingIntent: PendingIntent = PendingIntent.getBroadcast(this@MainActivity, 0,notifyIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT)

                        AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager,
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,0,notifyPendingIntent)
                    }
                }

                if(jsonmsg["action"] == "updateSensors"){
                    AppData.instance.sensorUpdate.value = 1
                }
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d(TAG, "Connection lost ${cause.toString()}")
                connect(this@MainActivity)
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })
        val options = MqttConnectOptions()
        options.userName = "smarthome"
        options.password = "smarthome".toCharArray()
        options.isCleanSession = false
        try {
            AppData.instance.mqttClient.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Connection success")
                    subscribe("/android", qos = 1)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Connection failure")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

 */
}