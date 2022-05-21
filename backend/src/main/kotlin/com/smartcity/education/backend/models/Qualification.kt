package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

/**
 * Data class representing a qualification
 * @param name Name of the qualification
 * @param description Description of the qualification
 * @param id Id of the qualification
 */
@Entity
data class Qualification(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("name", required = true)
    val name: String,

    @field:JsonProperty("description", required = true)
    val description: String,

    @field:ManyToMany(cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var educations: MutableList<Education> = ArrayList()
)

