package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Data class representing a graduation
 * @param date Date of the graduation
 * @param id Id of the graduation
 * @param matriculationId Id of the graduations matriculation
 */
@Entity
data class Graduation(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:JsonProperty("id")
    var id: Long? = null,

    @field:JsonProperty("date", required = true)
    var date: LocalDateTime,

    @field:ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var matriculation: Matriculation? = null,
) {
    @get:JsonProperty("matriculationId")
    val matriculationId: Long? get() = matriculation?.id
}

