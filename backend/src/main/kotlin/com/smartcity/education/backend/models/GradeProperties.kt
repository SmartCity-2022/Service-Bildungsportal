package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Property collection for a grade
 * @param grade Value of the grade
 * @param date Date of the publication
 * @param id Id of the grade
 * @param matriculationId Id of the grades matriculation
 */
data class GradeProperties(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("grade", required = true)
    val grade: Float,

    @field:JsonProperty("date", required = true)
    val date: String,

    @field:JsonProperty("matriculationId")
    val matriculationId: Long? = null
)

