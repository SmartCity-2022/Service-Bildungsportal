package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Matriculation
import com.smartcity.education.backend.models.MatriculationProperties
import org.springframework.stereotype.Component

@Component
class MatriculationAssigner: Assigner<MatriculationProperties, Matriculation> {
    override fun assign(from: MatriculationProperties, to: Matriculation) {
        to.id = from.id ?: to.id
        to.date = from.date ?: to.date
    }
}