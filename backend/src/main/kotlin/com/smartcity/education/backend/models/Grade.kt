package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Data class representing a grade
 * @param grade Value of the grade
 * @param date Date of the publication
 * @param id Id of the grade
 * @param assessmentId Id of the grades matriculation
 */
@Entity
data class Grade(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:JsonProperty("id")
    var id: Long? = null,

    @field:JsonProperty("grade", required = true)
    var grade: Float,

    @field:JsonProperty("date", required = true)
    var date: LocalDateTime,

    @field:ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var matriculation: Matriculation? = null,

    @field:ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var assessment: Assessment? = null
) {
    @Transient
    @JsonProperty("matriculationId")
    var matriculationId: Long? = null
        get() = matriculation?.id ?: field

    @Transient
    @JsonProperty("assessmentId")
    var assessmentId: Long? = null
        get() = assessment?.id ?: field
}

