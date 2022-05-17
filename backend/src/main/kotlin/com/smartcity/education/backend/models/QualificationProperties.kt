package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Property collection for a qualification
 * @param name Name of the qualification
 * @param description Description of the qualification
 * @param id Id of the qualification
 */
data class QualificationProperties(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("name")
    val name: String? = null,

    @field:JsonProperty("description")
    val description: String? = null
)

