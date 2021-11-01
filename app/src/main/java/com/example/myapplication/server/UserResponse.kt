package com.ipleiria.estg.meicm.arcismarthome.server

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserResponse: Serializable {
        var id: Int? = null
        var username: String? = null
        var token: String? = null
        var first_name: String? = null
        var last_name: String? = null
        var email: String? = null
}