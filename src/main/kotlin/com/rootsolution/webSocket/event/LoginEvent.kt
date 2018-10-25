package com.rootsolution.webSocket.event

import java.util.*

class LoginEvent(var username: String?) {
    var time: Date? = null

    init {
        time = Date()
    }
}
