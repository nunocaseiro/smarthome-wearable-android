package com.ipleiria.estg.meicm.arcismarthome.models

import java.util.*

data class User (var id: Int? = null, val username: String? = null, var email: String? = null, var firstName: String? = null, var lastName: String? = null, val token: String ){
    var favourites: MutableList<Favourite> = LinkedList<Favourite>()
    var notifications: MutableList<Notification> = LinkedList<Notification>()
}