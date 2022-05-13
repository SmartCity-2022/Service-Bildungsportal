package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Institution
import com.smartcity.education.backend.models.InstitutionProperties
import org.springframework.stereotype.Component

@Component
class InstitutionAssigner: Assigner<InstitutionProperties, Institution> {
    override fun assign(from: InstitutionProperties, to: Institution) {
        to.id = from.id ?: to.id
        to.name = from.name ?: to.name
    }
}