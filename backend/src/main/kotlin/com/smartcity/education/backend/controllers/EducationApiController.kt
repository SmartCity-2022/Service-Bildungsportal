package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.Constants
import com.smartcity.education.backend.MessageSender
import com.smartcity.education.backend.assigners.Assigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.Assessment
import com.smartcity.education.backend.models.Education
import com.smartcity.education.backend.models.EducationProperties
import com.smartcity.education.backend.models.Matriculation
import com.smartcity.education.backend.repositories.EducationRepository
import com.smartcity.education.backend.repositories.QualificationRepository
import com.smartcity.education.backend.repositories.StudentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class EducationApiController(
        private val repository: EducationRepository,
        private val qualificationRepository: QualificationRepository,
        private val studentRepository: StudentRepository,
        private val assigner: Assigner<EducationProperties, Education>,
        private val authUtil: AuthUtil,
        private val sender: MessageSender
) {

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/education/{id}/qualification"],
        consumes = ["application/json"]
    )
    fun addQualificationsOfEducation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody requestBody: List<Long>
    ): ResponseEntity<Unit> {
        val education = repository.findByIdOrNull(id)
        val qualifications = qualificationRepository.findAllById(requestBody)

        return education?.let {
            if (!authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), education.location?.institutionId ?: -1)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .build()
            }

            qualifications.forEach { q -> q.educations.add(it) }
            it.qualifications.addAll(qualifications)
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
        value = ["/education/{id}/assessment"],
        produces = ["application/json"]
    )
    fun allAssessmentsOfEducation(
        @PathVariable("id") id: Long
    ): ResponseEntity<List<Assessment>> {
        val education = repository.findByIdOrNull(id)

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
        val education = repository.findByIdOrNull(id)

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
        val education = repository.findByIdOrNull(id)

        return education?.let {
            ResponseEntity
                .ok(it.qualifications.map { q -> q.id ?: 0 })
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
        val education = repository.findByIdOrNull(id)

        return education?.let {
            if (!authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), education.location?.institutionId ?: -1)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .build()
            }

            assessment.education = it
            it.assessments.add(assessment)
            repository.save(it)

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
        if (!authUtil.hasStudentAuthority(SecurityContextHolder.getContext(), studentId)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build()
        }

        val education = repository.findByIdOrNull(id)

        return education?.let {
            val student = studentRepository.findByIdOrNull(studentId)
            val matriculation = Matriculation(
                education = it,
                student = student,
                date = LocalDateTime.now()
            )

            student?.matriculations?.add(matriculation)
            it.matriculations.add(matriculation)
            val created = repository.save(it)

            created.matriculations.last().id?.let { id ->
                sender.send(
                        Constants.RoutingKeys.matriculated,
                        id.toString()
                )
            }

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
        val education = repository.findByIdOrNull(educationId)

        return education?.let {
            if (!authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), education.location?.institutionId ?: -1)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .build()
            }

            it.qualifications.removeIf { q -> q.id == qualificationId }
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
        value = ["/education/{id}"],
        produces = ["application/json"]
    )
    fun singleEducation(
        @PathVariable("id") id: Long
    ): ResponseEntity<Education> {
        val education = repository.findByIdOrNull(id)

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
        val education = repository.findByIdOrNull(id)

        return education?.let {
            if (!authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), education.location?.institutionId  ?: -1)) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .build()
            }
            assigner.assign(educationProperties, it)
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
