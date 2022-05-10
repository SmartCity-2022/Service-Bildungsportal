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
 * Property collection for a graduation
 * @param date Date of the graduation
 * @param id Id of the graduation
 * @param matriculationId Id of the graduations matriculation
 */
data class GraduationProperties(

    @field:JsonProperty("date", required = true) val date: String,

    @field:JsonProperty("id") val id: Long? = null,

    @field:JsonProperty("matriculationId") val matriculationId: Long? = null
) {

}

