package com.smartcity.education.backend

fun interface MessageSender {
    fun send(routingKey: String, payload: Any)
}