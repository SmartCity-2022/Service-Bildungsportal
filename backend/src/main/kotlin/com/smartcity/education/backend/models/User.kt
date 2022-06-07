package com.smartcity.education.backend.models

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class User(
    @field:Id
    val email: String,

    val isAdmin: Boolean = false,

    @field:OneToOne(cascade = [CascadeType.DETACH])
    var student: Student? = null,

    @field:OneToOne(cascade = [CascadeType.DETACH])
    val institution: Institution? = null
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return ArrayList()
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return email!!
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}