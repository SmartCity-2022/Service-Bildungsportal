package com.smartcity.education.backend.repositories

import com.smartcity.education.backend.models.Student
import org.springframework.data.repository.CrudRepository

interface StudentRepository: CrudRepository<Student, Long> {
}