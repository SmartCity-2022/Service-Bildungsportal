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
 * Property collection for an institution
 * @param name Name of the institution
 * @param id Id of the institution
 */
data class InstitutionProperties(

    @field:JsonProperty("id") val id: Long? = null,

    @field:JsonProperty("name") val name: String? = null
) {

}

