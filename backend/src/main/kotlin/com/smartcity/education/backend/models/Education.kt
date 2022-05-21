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
    var id: Long? = null,

    @field:JsonProperty("title", required = true)
    var title: String,

    @field:JsonProperty("description", required = true)
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var location: Location? = null,

    @field:JsonIgnore
    @field:ManyToMany(cascade = [CascadeType.ALL])
    val qualifications: MutableList<Qualification> = ArrayList(),

    @field:JsonIgnore
    @field:OneToMany(mappedBy = "education", cascade = [CascadeType.ALL])
    val assessments: MutableList<Assessment> = ArrayList(),

    @field:JsonIgnore
    @field:OneToMany(mappedBy = "education", cascade = [CascadeType.ALL])
    val matriculations: MutableList<Matriculation> = ArrayList()
) {
    @get:JsonProperty("locationId")
    val locationId: Long? get() = location?.id
}

