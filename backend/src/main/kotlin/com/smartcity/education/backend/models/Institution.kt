package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class representing an institution
 * @param name Name of the institution
 * @param id Id of the institution
 */
data class Institution(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("name", required = true)
    val name: String,
)

