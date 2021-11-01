package com.example.myapplication.models



import com.example.myapplication.R
import java.io.Serializable

data class Sensor (var id: Int?, var name: String?, var sensortype: String?, var value: Double?, var gpio: Int?, var room: Int?) : Serializable {
    var icon: Int = 0
    var status: String = ""
    var roomName: String = ""
    var favorite: Boolean = true

    init {
        val rooms = AppData.instance.home.rooms
        for (i in rooms) {
            if (i.id == room) {
                roomName = i.name
                break
            }
        }

        when (sensortype) {
            "led" -> {
                icon = R.drawable.ic_led_border

                if(value == 0.0) status = "Off"
                else status = "On"
            }
            "camera" -> {
                icon = R.drawable.ic_camera_border

                if(value == 0.0) status = "Off"
                else status = "On"
            }
            "plug" -> {
                icon = R.drawable.ic_plug_border

                if(value == 0.0) status = "Off"
                else status = "On"
            }
            "servo" -> {
                icon = R.drawable.ic_servo_border

                if(value == 0.0) status = "Close"
                else status = "Open"
            }
            "motion" -> {
                icon = R.drawable.ic_no_icon_border_24dp

                if(value == 0.0) status = "Off"
                else status = "On"
            }
        }
    }
}