package com.smartcity.education.backend.repositories

import com.smartcity.education.backend.models.Education
import org.springframework.data.repository.CrudRepository

interface EducationRepository: CrudRepository<Education, Long> {
}