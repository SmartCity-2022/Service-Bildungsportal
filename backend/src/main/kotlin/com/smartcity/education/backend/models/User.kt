package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class User(
    @field:Id
    @field:JsonProperty("email")
    val email: String,

    @field:JsonProperty("isAdmin")
    val isAdmin: Boolean = false,

    @field:OneToOne(cascade = [CascadeType.DETACH])
    @field:JsonProperty("student")
    var student: Student? = null,

    @field:OneToOne(cascade = [CascadeType.DETACH])
    @field:JsonProperty("institution")
    val institution: Institution? = null
): UserDetails {
    @JsonIgnore
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return ArrayList()
    }

    @JsonIgnore
    override fun getPassword(): String {
        return ""
    }

    @JsonIgnore
    override fun getUsername(): String {
        return email!!
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return false
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isEnabled(): Boolean {
        return true
    }
}