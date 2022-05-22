package com.smartcity.education.backend.assigners

import com.smartcity.education.backend.models.Student
import com.smartcity.education.backend.models.StudentProperties
import org.springframework.stereotype.Component

@Component
class StudentAssigner: Assigner<StudentProperties, Student> {
    override fun assign(from: StudentProperties, to: Student) {
        to.name = from.name ?: to.name
    }
}