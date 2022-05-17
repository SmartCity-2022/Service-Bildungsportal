package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Property collection for an institution
 * @param name Name of the institution
 * @param id Id of the institution
 */
data class InstitutionProperties(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("name")
    val name: String? = null
)

