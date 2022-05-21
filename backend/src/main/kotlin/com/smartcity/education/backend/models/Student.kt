package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

/**
 * Data class representing a student
 * @param name Name of the student
 * @param id Id of the student
 */
@Entity
data class Student(
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:JsonProperty("id")
    val id: Long? = null,

    @field:JsonProperty("name", required = true)
    val name: String,

    @field:OneToMany(mappedBy = "student", cascade = [CascadeType.ALL])
    @field:JsonIgnore
    val matriculations: MutableList<Matriculation> = ArrayList()
)

