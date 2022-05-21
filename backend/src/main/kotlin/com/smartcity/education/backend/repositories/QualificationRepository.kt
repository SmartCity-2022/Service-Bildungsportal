package com.smartcity.education.backend.repositories

import com.smartcity.education.backend.models.Qualification
import org.springframework.data.repository.CrudRepository

interface QualificationRepository: CrudRepository<Qualification, Long> {
}