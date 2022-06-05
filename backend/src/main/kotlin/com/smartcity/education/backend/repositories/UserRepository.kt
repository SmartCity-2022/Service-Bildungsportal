package com.smartcity.education.backend.repositories

import com.smartcity.education.backend.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, String> {
}