package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.QualificationAssigner
import com.smartcity.education.backend.models.Qualification
import com.smartcity.education.backend.models.QualificationProperties
import com.smartcity.education.backend.repositories.EducationRepository
import com.smartcity.education.backend.repositories.QualificationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class QualificationApiController(
        private val repository: QualificationRepository,
        private val educationRepository: EducationRepository,
        private val assigner: QualificationAssigner
) {
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/qualification/{id}/education"],
        consumes = ["application/json"]
    )
    fun addEducationsOfQualification(
        @PathVariable("id") id: Long,
        @Valid @RequestBody requestBody: List<Long>
    ): ResponseEntity<Unit> {
        val qualification = repository.findByIdOrNull(id)
        val educations = educationRepository.findAllById(requestBody)

        return qualification?.let {
            educations.forEach { e -> e.qualifications.add(it) }
            it.educations.addAll(educations)
            repository.save(it)

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
        value = ["/qualification/{id}/education"],
        produces = ["application/json"]
    )
    fun allEducationsOfQualification(
        @PathVariable("id") id: Long
    ): ResponseEntity<List<Long>> {
        val qualification = repository.findByIdOrNull(id)

        return qualification?.let {
            ResponseEntity
                .ok(it.educations.map { e -> e.id ?: 0 })
        } ?:
        ResponseEntity
            .notFound()
            .build()
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/qualification"],
        produces = ["application/json"]
    )
    fun allQualifications(): ResponseEntity<Iterable<Qualification>> {
        val qualifications = repository.findAll()

        return ResponseEntity
            .ok(qualifications)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/qualification"],
        consumes = ["application/json"]
    )
    fun createQualification(
        @Valid @RequestBody qualification: Qualification
    ): ResponseEntity<Unit> {
        repository.save(qualification)

        return ResponseEntity
            .created(URI("/qualification/${qualification.id}"))
            .build()
    }

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/qualification/{qualificationId}/education/{educationId}"]
    )
    fun removeEducationsOfQualification(
        @PathVariable("qualificationId") qualificationId: Long,
        @PathVariable("educationId") educationId: Long
    ): ResponseEntity<Unit> {
        val qualification = repository.findByIdOrNull(qualificationId)

        return qualification?.let {
            it.educations.removeIf { e -> e.id == educationId }
            repository.save(it)

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
        value = ["/qualification/{id}"],
        produces = ["application/json"]
    )
    fun singleQualification(
        @PathVariable("id") id: Long
    ): ResponseEntity<Qualification> {
        val qualification = repository.findByIdOrNull(id)

        return qualification?.let {
            ResponseEntity
                .ok(it)
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/qualification/{id}"],
        consumes = ["application/json"]
    )
    fun updateQualification(
        @PathVariable("id") id: Long,
        @Valid @RequestBody qualificationProperties: QualificationProperties
    ): ResponseEntity<Unit> {
        val qualification = repository.findByIdOrNull(id)

        return qualification?.let {
            assigner.assign(qualificationProperties, it)
            repository.save(it)

            ResponseEntity
                .ok()
                .build()
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }
}
