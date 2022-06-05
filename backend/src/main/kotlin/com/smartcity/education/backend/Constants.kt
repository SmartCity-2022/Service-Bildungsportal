package com.smartcity.education.backend

object Constants {
    val exchange = System.getenv("RABBITMQ_EXCHANGE") ?: "smartcity"

    object RoutingKeys {
        const val created = "service.education.created"
        const val hello = "service.hello"
        const val world = "service.world"
    }
}