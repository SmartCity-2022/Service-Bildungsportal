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
 * Property collection for an education
 * @param title Title of the education
 * @param description Description of the education
 * @param id Id of the education
 * @param locationId Id of the educations location
 */
data class EducationProperties(

    @field:JsonProperty("id") val id: Long? = null,

    @field:JsonProperty("locationId") val locationId: Long? = null,

    @field:JsonProperty("title") val title: String? = null,

    @field:JsonProperty("description") val description: String? = null
) {

}

