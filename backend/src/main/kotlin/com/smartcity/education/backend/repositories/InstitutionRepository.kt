package com.smartcity.education.backend.repositories

import com.smartcity.education.backend.models.Institution
import org.springframework.data.repository.CrudRepository

interface InstitutionRepository: CrudRepository<Institution, Long> {
}