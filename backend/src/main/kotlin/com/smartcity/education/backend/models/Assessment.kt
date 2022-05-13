package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class representing an assessment
 * @param educationId Id of the assessments education
 * @param id Id of the assessment
 */
data class Assessment(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("educationId", required = true)
    val educationId: Long
)

