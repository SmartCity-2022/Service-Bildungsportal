package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class representing a graduation
 * @param date Date of the graduation
 * @param id Id of the graduation
 * @param matriculationId Id of the graduations matriculation
 */
data class Graduation(

    @field:JsonProperty("date", required = true) val date: String,

    @field:JsonProperty("id") val id: Long? = null,

    @field:JsonProperty("matriculationId") val matriculationId: Long? = null
) {

}

