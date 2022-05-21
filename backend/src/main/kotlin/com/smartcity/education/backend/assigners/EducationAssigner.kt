package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Education
import com.smartcity.education.backend.models.EducationProperties
import org.springframework.stereotype.Component

@Component
class EducationAssigner: Assigner<EducationProperties, Education> {
    override fun assign(from: EducationProperties, to: Education) {
        to.id = from.id ?: to.id
        to.title = from.title ?: to.title
        to.description = from.description ?: to.description
    }
}