package com.smartcity.education.backend.repositories

import com.smartcity.education.backend.models.Graduation
import org.springframework.data.repository.CrudRepository

interface GraduationRepository: CrudRepository<Graduation, Long> {
}