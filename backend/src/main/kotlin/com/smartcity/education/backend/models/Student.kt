package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class representing a student
 * @param name Name of the student
 * @param id Id of the student
 */
data class Student(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("name", required = true)
    val name: String
)

