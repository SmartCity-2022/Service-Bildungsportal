package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Property collection for a matriculation
 * @param date Date of the matriculation
 * @param id Id of the matriculation
 * @param studentId Id of the matriculation's student
 * @param educationId Id of the matriculation's education
 */
data class MatriculationProperties(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("studentId")
    val studentId: Long? = null,

    @field:JsonProperty("educationId")
    val educationId: Long? = null,

    @field:JsonProperty("date")
    val date: String? = null
)

