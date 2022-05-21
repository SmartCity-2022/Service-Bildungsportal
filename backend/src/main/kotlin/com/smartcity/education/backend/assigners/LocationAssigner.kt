package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Location
import com.smartcity.education.backend.models.LocationProperties
import org.springframework.stereotype.Component

@Component
class LocationAssigner: Assigner<LocationProperties, Location> {
    override fun assign(from: LocationProperties, to: Location) {
        to.id = from.id ?: to.id
        to.address = from.address ?: to.address
        to.city = from.city ?: to.city
        to.zip = from.zip ?: to.zip
    }
}