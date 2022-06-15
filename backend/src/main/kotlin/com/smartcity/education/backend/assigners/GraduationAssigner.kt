package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.*
import org.springframework.stereotype.Component

@Component
class GraduationAssigner: Assigner<GraduationProperties, Graduation> {
    override fun assign(from: GraduationProperties, to: Graduation) {
        to.id = from.id ?: to.id
        to.date = from.date ?: to.date
    }
}