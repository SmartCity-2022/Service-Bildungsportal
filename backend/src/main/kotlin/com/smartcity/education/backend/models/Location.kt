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
    var id: Long? = null,

    @field:JsonProperty("address", required = true)
    var address: String,

    @field:JsonProperty("zip", required = true)
    var zip: String,

    @field:JsonProperty("city", required = true)
    var city: String,

    @field:ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var institution: Institution? = null,

    @field:JsonIgnore
    @field:OneToMany(mappedBy = "location", cascade = [CascadeType.ALL])
    val educations: MutableList<Education> = ArrayList(),
) {
    @get:JsonProperty("institutionId")
    val institutionId: Long? get() = institution?.id
}
