package com.smartcity.education.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Data class representing a matriculation
 * @param date Date of the matriculation
 * @param id Id of the matriculation
 * @param studentId Id of the matriculation's student
 * @param educationId Id of the matriculation's education
 */
@Entity
data class Matriculation(
    @field:Id
    @field:GeneratedValue
    @field:JsonProperty("id")
    var id: Long? = null,

    @field:JsonProperty("date", required = true)
    var date: LocalDateTime,

    @field:ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var education: Education? = null,

    @field:ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @field:JsonIgnore
    var student: Student? = null,

    @field:JsonIgnore
    @field:OneToMany(mappedBy = "matriculation", cascade = [CascadeType.ALL])
    val graduations: MutableList<Graduation> = ArrayList(),

    @field:JsonIgnore
    @field:OneToMany(mappedBy = "matriculation", cascade = [CascadeType.ALL])
    val grades: MutableList<Grade> = ArrayList()
) {
    @get:JsonProperty("educationId")
    val educationId: Long? get() = education?.id

    @get:JsonProperty("studentId")
    val studentId: Long? get() = student?.id
}

