package com.smartcity.education.backend.models

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import javax.validation.Valid

/**
 * Property collection for a matriculation
 * @param date Date of the matriculation
 * @param id Id of the matriculation
 * @param studentId Id of the matriculations student
 * @param educationId Id of the matriculations education
 */
data class MatriculationProperties(

    @field:JsonProperty("id") val id: Long? = null,

    @field:JsonProperty("studentId") val studentId: Long? = null,

    @field:JsonProperty("educationId") val educationId: Long? = null,

    @field:JsonProperty("date") val date: String? = null
) {

}

