package com.smartcity.education.backend.repositories

import com.smartcity.education.backend.models.Matriculation
import com.smartcity.education.backend.models.User
import org.springframework.data.repository.CrudRepository

interface MatriculationRepository: CrudRepository<Matriculation, Long> {
    fun findMatriculationsByStudent_Id(studentId: Long): Iterable<Matriculation>
}