package com.smartcity.education.backend.repositories

import com.smartcity.education.backend.models.Grade
import org.springframework.data.repository.CrudRepository

interface GradeRepository: CrudRepository<Grade, Long> {
}