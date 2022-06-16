package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Property collection for an assessment
 * @param educationId Id of the assessments education
 * @param id Id of the assessment
 */
data class AssessmentProperties(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("title")
    val title: String? = null,

    @field:JsonProperty("educationId")
    val educationId: Long? = null
)

