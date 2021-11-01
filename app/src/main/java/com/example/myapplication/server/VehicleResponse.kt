package com.ipleiria.estg.meicm.arcismarthome.server

import com.ipleiria.estg.meicm.arcismarthome.database.VehicleDataModel
import com.ipleiria.estg.meicm.arcismarthome.database.VehicleDatabase
import com.squareup.moshi.JsonClass
import java.io.Serializable


@JsonClass(generateAdapter = true)
data class VehicleContainer(val videos: List<NetworkVehicle>)


@JsonClass(generateAdapter = true)
data class NetworkVehicle(
    val id: Int ,val brand: String, val licenseplate: String, val model: String, val year: Int)


/**
 * Convert Network results to database objects
 */
fun VehicleContainer.asDatabaseModel(): List<VehicleDataModel> {
    return videos.map {
        VehicleDataModel(
            id = it.id,
            brand = it.brand,
            licenseplate = it.licenseplate,
            model = it.model,
            year = it.year)
    }
}