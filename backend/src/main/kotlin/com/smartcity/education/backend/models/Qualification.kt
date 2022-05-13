package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class representing a qualification
 * @param name Name of the qualification
 * @param description Description of the qualification
 * @param id Id of the qualification
 */
data class Qualification(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("name", required = true)
    val name: String,

    @field:JsonProperty("description", required = true)
    val description: String
)

