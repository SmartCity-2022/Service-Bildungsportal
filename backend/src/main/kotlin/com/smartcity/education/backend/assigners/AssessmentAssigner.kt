package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Assessment
import com.smartcity.education.backend.models.AssessmentProperties
import com.smartcity.education.backend.models.Education
import com.smartcity.education.backend.models.EducationProperties
import org.springframework.stereotype.Component

@Component
class AssessmentAssigner: Assigner<AssessmentProperties, Assessment> {
    override fun assign(from: AssessmentProperties, to: Assessment) {
        to.id = from.id ?: to.id
        to.title = from.title ?: to.title
    }
}