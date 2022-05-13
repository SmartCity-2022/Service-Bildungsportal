package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Property collection for a graduation
 * @param date Date of the graduation
 * @param id Id of the graduation
 * @param matriculationId Id of the graduations matriculation
 */
data class GraduationProperties(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("date", required = true)
    val date: String,

    @field:JsonProperty("matriculationId")
    val matriculationId: Long? = null
)

