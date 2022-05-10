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
 * Property collection an location
 * @param address Address of the location
 * @param zip Zip code of the location
 * @param city City of the location
 * @param id Id of the location
 */
data class LocationProperties(

    @field:JsonProperty("id") val id: Long? = null,

    @field:JsonProperty("address") val address: String? = null,

    @field:JsonProperty("zip") val zip: String? = null,

    @field:JsonProperty("city") val city: String? = null
) {

}

