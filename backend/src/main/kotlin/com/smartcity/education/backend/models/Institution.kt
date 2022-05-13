package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

/**
 * Data class representing an institution
 * @param name Name of the institution
 * @param id Id of the institution
 */
@Entity
data class Institution(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:JsonProperty("id")
    var id: Long? = null,

    @field:JsonProperty("name", required = true)
    var name: String,

    @field:JsonIgnore
    @field:OneToMany(mappedBy = "institution", cascade = [CascadeType.ALL])
    val locations: MutableList<Location> = ArrayList()
)

