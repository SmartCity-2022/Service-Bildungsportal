package com.smartcity.education.backend.repositories

import com.smartcity.education.backend.models.Institution
import com.smartcity.education.backend.models.Location
import org.springframework.data.repository.CrudRepository

interface LocationRepository: CrudRepository<Location, Long> {
}