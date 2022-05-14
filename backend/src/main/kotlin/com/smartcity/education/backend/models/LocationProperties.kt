package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Property collection a location
 * @param address Address of the location
 * @param zip Zip code of the location
 * @param city City of the location
 * @param id Id of the location
 */
data class LocationProperties(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("address")
    val address: String? = null,

    @field:JsonProperty("zip")
    val zip: String? = null,

    @field:JsonProperty("city")
    val city: String? = null,

    @field:JsonProperty("institutionId")
    val institutionId: String? = null
)

