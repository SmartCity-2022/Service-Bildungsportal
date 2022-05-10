package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class representing an assessment
 * @param educationId Id of the assesments education
 * @param id Id of the assessment
 */
data class Assessment(

    @field:JsonProperty("educationId", required = true) val educationId: Long,

    @field:JsonProperty("id") val id: Long? = null
) {

}

