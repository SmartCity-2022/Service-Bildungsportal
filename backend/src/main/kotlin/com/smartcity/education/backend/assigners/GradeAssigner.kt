package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Grade
import com.smartcity.education.backend.models.GradeProperties
import com.smartcity.education.backend.models.Student
import com.smartcity.education.backend.models.StudentProperties
import org.springframework.stereotype.Component

@Component
class GradeAssigner: Assigner<GradeProperties, Grade> {
    override fun assign(from: GradeProperties, to: Grade) {
        to.id = from.id ?: to.id
        to.date = from.date ?: to.date
        to.grade = from.grade ?: to.grade
    }
}