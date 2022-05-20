package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

/**
 * Data class representing an location
 * @param address Address of the location
 * @param zip Zip code of the location
 * @param city City of the location
 * @param id Id of the location
 */
@Entity
data class Location(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("address", required = true)
    val address: String,

    @field:JsonProperty("zip", required = true)
    val zip: String,

    @field:JsonProperty("city", required = true)
    val city: String,

    @field:ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var institution: Institution? = null,
)

