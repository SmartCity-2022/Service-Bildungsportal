package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

/**
 * Data class representing an assessment
 * @param educationId Id of the assessments education
 * @param id Id of the assessment
 */
@Entity
data class Assessment(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:JsonProperty("id")
    var id: Long? = null,

    @field:JsonProperty("title")
    var title: String,

    @field:ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var education: Education? = null,

    @field:JsonIgnore
    @field:OneToMany(mappedBy = "assessment", cascade = [CascadeType.ALL])
    val grades: MutableList<Grade> = ArrayList()
) {
    @get:JsonProperty("educationId")
    val educationId: Long? get() = education?.id
}

