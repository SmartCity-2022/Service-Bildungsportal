package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.Assigner
import com.smartcity.education.backend.models.Assessment
import com.smartcity.education.backend.models.Education
import com.smartcity.education.backend.models.EducationProperties
import com.smartcity.education.backend.models.Matriculation
import com.smartcity.education.backend.repositories.EducationRepository
import com.smartcity.education.backend.repositories.QualificationRepository
import com.smartcity.education.backend.repositories.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import java.net.URI
import java.time.LocalDateTime

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class EducationApiController {
    @Autowired
    private val repository: EducationRepository? = null
    @Autowired
    private val qualificationRepository: QualificationRepository? = null
    @Autowired
    private val studentRepository: StudentRepository? = null
    @Autowired
    private val assigner: Assigner<EducationProperties, Education>? = null

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/education/{id}/qualification"],
        consumes = ["application/json"]
    )
    fun addQualificationsOfEducation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody requestBody: List<Long>
    ): ResponseEntity<Unit> {
        val education = repository?.findByIdOrNull(id)
        val qualifications = qualificationRepository?.findAllById(requestBody)

        return education?.let {
            qualifications?.forEach { q -> q.educations.add(it) }
            it.qualifications.addAll(qualifications ?: emptyList())
            repository?.save(it)

            ResponseEntity
                .created(URI("/education/$id/qualification"))
                .build()
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/education/{id}/assessment"],
        produces = ["application/json"]
    )
    fun allAssessmentsOfEducation(
        @PathVariable("id") id: Long
    ): ResponseEntity<List<Assessment>> {
        val education = repository?.findByIdOrNull(id)

        return education?.let {
            ResponseEntity
                .ok(it.assessments)
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/education/{id}/matriculation"],
        produces = ["application/json"]
    )
    fun allMatriculationsOfEducation(
        @PathVariable("id") id: Long
    ): ResponseEntity<List<Matriculation>> {
        val education = repository?.findByIdOrNull(id)

        return education?.let {
            ResponseEntity
                .ok(it.matriculations)
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/education/{id}/qualification"],
        produces = ["application/json"]
    )
    fun allQualificationsOfEducation(
        @PathVariable("id") id: Long
    ): ResponseEntity<List<Long>> {
        val education = repository?.findByIdOrNull(id)

        return education?.let {
            ResponseEntity
                .ok(it.qualifications.map { it.id ?: 0 })
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/education/{id}/assessment"],
        consumes = ["application/json"]
    )
    fun createAssessmentOfEducation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody assessment: Assessment
    ): ResponseEntity<Unit> {
        val education = repository?.findByIdOrNull(id)

        return education?.let {
            assessment.education = it
            it.assessments.add(assessment)
            repository?.save(it)

            ResponseEntity
                .created(URI("/assessment/${assessment.id}"))
                .build()
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/education/{id}/matriculation"],
        consumes = ["application/json"]
    )
    fun createMatriculationOfEducation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody studentId: Long
    ): ResponseEntity<Unit> {
        val education = repository?.findByIdOrNull(id)

        return education?.let {
            val student = studentRepository?.findByIdOrNull(studentId)
            val matriculation = Matriculation(
                education = it,
                student = student,
                date = LocalDateTime.now()
            )

            student?.let { it.matriculations.add(matriculation) }
            it.matriculations.add(matriculation)
            repository?.save(it)

            ResponseEntity
                .created(URI("/matriculation/${matriculation.id}"))
                .build()
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/education/{educationId}/qualification/{qualificationId}"]
    )
    fun removeQualificationsOfEducation(
        @PathVariable("educationId") educationId: Long,
        @PathVariable("qualificationId") qualificationId: Long
    ): ResponseEntity<Unit> {
        val education = repository?.findByIdOrNull(educationId)

        return education?.let {
            it.qualifications.removeIf { it.id == qualificationId }
            repository?.save(it)

            ResponseEntity
                .ok()
                .build()
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/education/{id}"],
        produces = ["application/json"]
    )
    fun singleEducation(
        @PathVariable("id") id: Long
    ): ResponseEntity<Education> {
        val education = repository?.findByIdOrNull(id)

        return education?.let {
            ResponseEntity
                .ok(it)
        } ?:
        ResponseEntity
            .notFound()
            .build()
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/education/{id}"],
        consumes = ["application/json"]
    )
    fun updateEducationOfLocation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody educationProperties: EducationProperties
    ): ResponseEntity<Unit> {
        val education = repository?.findByIdOrNull(id)

        return education?.let {
            assigner?.assign(educationProperties, it)
            repository?.save(it)

            ResponseEntity
                .ok()
                .build()
        } ?:
        ResponseEntity
            .notFound()
            .build()
    }
}
