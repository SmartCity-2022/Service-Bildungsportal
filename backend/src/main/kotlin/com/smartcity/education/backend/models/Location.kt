package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class representing an location
 * @param address Address of the location
 * @param zip Zip code of the location
 * @param city City of the location
 * @param id Id of the location
 */
data class Location(
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("address", required = true)
    val address: String,

    @field:JsonProperty("zip", required = true)
    val zip: String,

    @field:JsonProperty("city", required = true)
    val city: String,
)

