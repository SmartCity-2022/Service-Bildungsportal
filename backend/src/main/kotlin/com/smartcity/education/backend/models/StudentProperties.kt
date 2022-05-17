package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Property collection for a student
 * @param name Name of the student
 * @param id Id of the student
 */
data class StudentProperties(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("name")
    val name: String? = null
)

