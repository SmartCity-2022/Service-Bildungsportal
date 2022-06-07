package com.smartcity.education.backend.authentication

import com.smartcity.education.backend.Constants
import com.smartcity.education.backend.models.User
import com.smartcity.education.backend.repositories.UserRepository
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Component

@Component
class AuthUtil(
        private val userRepository: UserRepository
) {
    fun hasAuthority(context: SecurityContext, authority: String): Boolean {
        return context.authentication.authorities.any { it.authority.equals(Constants.Authorities.student) }
    }

    fun hasInstitutionAuthority(context: SecurityContext, id: Long): Boolean {
        return hasAuthority(context, Constants.Authorities.institution)
                && checkUser(context) { user -> user.institution?.id == id}
    }

    fun hasStudentAuthority(context: SecurityContext, id: Long): Boolean {
        return hasAuthority(context, Constants.Authorities.student)
                && checkUser(context) { user -> user.student?.id == id}
    }

    fun updateUser(context: SecurityContext, callback: (u: User) -> Unit) {
        val principal = context.authentication.principal
        if (principal is User) {
            callback(principal)
            userRepository.save(principal)
        }
    }

    fun checkUser(context: SecurityContext, callback: (u: User) -> Boolean): Boolean {
        val principal = context.authentication.principal
        if (principal is User) {
            return callback(principal)
        }
        return false
    }
}