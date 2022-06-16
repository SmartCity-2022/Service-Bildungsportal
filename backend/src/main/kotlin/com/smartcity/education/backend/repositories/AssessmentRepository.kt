package com.smartcity.education.backend.repositories

import com.smartcity.education.backend.models.Assessment
import org.springframework.data.repository.CrudRepository

interface AssessmentRepository: CrudRepository<Assessment, Long> {
}