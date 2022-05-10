package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class representing an education
 * @param title Title of the education
 * @param description Description of the education
 * @param id Id of the education
 * @param locationId Id of the educations location
 */
data class Education(

    @field:JsonProperty("title", required = true) val title: String,

    @field:JsonProperty("description", required = true) val description: String,

    @field:JsonProperty("id") val id: Long? = null,

    @field:JsonProperty("locationId") val locationId: Long? = null
) {

}

