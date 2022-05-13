package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Property collection for an education
 * @param title Title of the education
 * @param description Description of the education
 * @param id Id of the education
 * @param locationId Id of the educations location
 */
data class EducationProperties(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("locationId")
    val locationId: Long? = null,

    @field:JsonProperty("title")
    val title: String? = null,

    @field:JsonProperty("description")
    val description: String? = null
)

