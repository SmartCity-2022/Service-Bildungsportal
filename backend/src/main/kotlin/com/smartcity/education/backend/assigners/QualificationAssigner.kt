package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Qualification
import com.smartcity.education.backend.models.QualificationProperties
import org.springframework.stereotype.Component

@Component
class QualificationAssigner: Assigner<QualificationProperties, Qualification> {
    override fun assign(from: QualificationProperties, to: Qualification) {
        to.name = from.name ?: to.name
        to.description = from.description ?: to.description
    }
}