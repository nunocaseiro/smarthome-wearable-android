package com.example.myapplication.server

import com.ipleiria.estg.meicm.arcismarthome.models.Notification
import com.ipleiria.estg.meicm.arcismarthome.models.Vehicle
import com.ipleiria.estg.meicm.arcismarthome.models.Room
import com.example.myapplication.models.Sensor
import com.ipleiria.estg.meicm.arcismarthome.server.*
import retrofit2.Call
import retrofit2.http.*

interface  UserService {

    // region Login/Register and User
    @POST("dj-rest-auth/login/")
    fun loginUser(@Body loginRequest: LoginRequest?): Call<LoginResponse?>?

    //Call<RegisterResponse> registerUser()
    
    @GET("api/userprofile/")
    fun populateUserWHome(@Header("Authorization") token: String, @Query("username") username: String?): Call<List<ProfileResponse>>
    // endregion


    // region Dashboard Fragment
    @GET("data/2.5/weather?units=metric")
    fun getWeatherOfHome(@Query("lat") lat: String, @Query("lon") lon: String, @Query("appid") appid: String): Call<WeatherResponse>

    @GET("api/statistics")
    fun getStatistics(@Header("Authorization") token: String, @Query("home") home: Int): Call<StatisticsResponse>
    // endregion

    @GET("api/sensors/")
    fun getAllSensors(@Header("Authorization") token: String) : Call<List<Sensor>>

    // region House Fragment
    @GET("api/actualrooms/")
    suspend fun populateRooms(@Header("Authorization") token: String) : List<Room>

    @GET("api/sensorsofroom/")
    suspend fun populateSensorsByRoom(@Header("Authorization") token: String, @Query("room") room: Int) : List<Sensor>

    @POST("api/sensors/")
    fun addSensor(@Header("Authorization") token: String, @Body sensor: Sensor): Call<Sensor>

    @POST("api/sensorsvalues/")
    fun updateSensorValue(@Header("Authorization") token: String, @Body sensorValue: SensorValueRequest) : Call<Void>

    @PUT("api/sensors/{id}/")
    fun updateSensor(@Header("Authorization") token: String, @Path("id") id: Int, @Body sensor: Sensor) : Call<Void>

    @DELETE("api/sensors/{id}/")
    fun deleteSensor(@Header("Authorization") token: String, @Path ("id") id: Int): Call<Void>
    // endregion


    // region Vehicles Fragment
    @GET("api/vehiclesofhome")
    suspend fun  getVehicles(@Header("Authorization") token: String, @Query("home") home: Int): List<NetworkVehicle>

    @Headers("Content-Type: application/json")
    @PUT("api/vehicles/{id}/")
    fun updateVehicle(@Header("Authorization") token: String, @Path ("id") id: Int?, @Body vehicle: Vehicle): Call<NetworkVehicle>

    @DELETE("api/vehicles/{id}/")
    fun deleteVehicle(@Header("Authorization") token: String, @Path ("id") id: Int): Call<NetworkVehicle>

    @POST("api/vehicles/")
    fun insertVehicle(@Header("Authorization") token: String, @Body vehicle: Vehicle): Call<NetworkVehicle>
    // endregion


    // region Notifications Fragment
    @GET("api/getNotificationsByUser")
    suspend fun getNotifications(@Header("Authorization") token: String, @Query("user") user: Int): List<Notification>

    @Headers("Content-Type: application/json")
    @PUT("api/notifications/{id}/")
    fun updateNotification(@Header("Authorization") token: String, @Path ("id") id: Int?, @Body notification: Notification): Call<Notification>

    @DELETE("api/notifications/{id}/")
    fun deleteNotification(@Header("Authorization") token: String, @Path ("id") id: Int): Call<Notification>
    // endregion
}