package com.ipleiria.estg.meicm.arcismarthome.server

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProfileResponse: Serializable {

    @SerializedName("user")
    var user: UserResponse? = null

    @SerializedName("home")
    var home: HomeResponse? = null

    @SerializedName("favourites")
    var favourites: List<FavouritesResponse>? = null

    @SerializedName("notifications")
    var notifications: List<FavouritesResponse>? = null
}