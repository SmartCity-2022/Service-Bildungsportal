package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

/**
 * Data class representing an education
 * @param title Title of the education
 * @param description Description of the education
 * @param id Id of the education
 * @param locationId Id of the educations location
 */
@Entity
data class Education(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("title", required = true)
    val title: String,

    @field:JsonProperty("description", required = true)
    val description: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var location: Location? = null
)

