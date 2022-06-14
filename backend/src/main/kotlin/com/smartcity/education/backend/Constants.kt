package com.smartcity.education.backend

object Constants {
    val exchange = System.getenv("RABBITMQ_EXCHANGE") ?: "smartcity"
    val mainHub = System.getenv("MAINHUB") ?: ""

    object RoutingKeys {
        const val created = "service.education.created"
        const val hello = "service.hello"
        const val world = "service.world"
    }

    object Authorization {
        const val header = "Authorization"
        const val bearer = "Bearer"
    }

    object Authorities {
        const val admin = "Admin"
        const val institution = "Institution"
        const val student = "Student"
    }
}