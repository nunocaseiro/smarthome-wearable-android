package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class SensorsViewAdapter(private val activity: Activity, sensorsList: ArrayList<com.example.myapplication.models.Sensor>) : BaseAdapter() {

    private var sensorsList = ArrayList<com.example.myapplication.models.Sensor>()

    init {
        this.sensorsList = sensorsList
    }

    override fun getCount(): Int {
        return sensorsList.size
    }

    override fun getItem(i: Int): Any {
        return i
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }


    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var vi: View
        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        vi = inflater.inflate(R.layout.listview_item, null)
        val title = vi.findViewById<TextView>(R.id.name)
        val image = vi.findViewById<ImageView>(R.id.sensor_img)
        title.text = sensorsList[i].name
        image?.setImageResource(sensorsList[i].icon)
        return vi
    }
}

