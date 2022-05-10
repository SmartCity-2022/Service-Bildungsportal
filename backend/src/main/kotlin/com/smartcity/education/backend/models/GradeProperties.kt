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
 * Property collection for a grade
 * @param grade Value of the grade
 * @param date Date of the publication
 * @param id Id of the grade
 * @param matriculationId Id of the grades matriculation
 */
data class GradeProperties(

    @field:JsonProperty("grade", required = true) val grade: Float,

    @field:JsonProperty("date", required = true) val date: String,

    @field:JsonProperty("id") val id: Long? = null,

    @field:JsonProperty("matriculationId") val matriculationId: Long? = null
) {

}

