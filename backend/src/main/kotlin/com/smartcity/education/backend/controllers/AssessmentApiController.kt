package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.AssessmentAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.AssessmentProperties
import com.smartcity.education.backend.models.Grade
import com.smartcity.education.backend.repositories.AssessmentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import java.net.URI

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class AssessmentApiController(
        private val repository: AssessmentRepository,
        private val assigner: AssessmentAssigner,
        private val authUtil: AuthUtil
) {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/assessment/{id}/grade"],
        produces = ["application/json"]
    )
    fun allGradesOfAssessment(
        @PathVariable("id") id: Long
    ): ResponseEntity<List<Grade>> {
        val assessment = repository.findByIdOrNull(id)

        return assessment?.let {
            if (authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), it.education?.location?.institutionId ?: -1)) {
                ResponseEntity
                        .ok(it.grades)
            } else if (authUtil.checkUser(SecurityContextHolder.getContext()) { it.student != null }) {
                val user = authUtil.getUser(SecurityContextHolder.getContext())
                ResponseEntity
                        .ok(it.grades.filter { it.matriculation?.studentId == user.student?.id})
            } else {
                ResponseEntity
                        .badRequest()
                        .build()
            }
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/assessment/{id}/grade"],
        consumes = ["application/json"]
    )
    fun createGradeOfAssessment(
        @PathVariable("id") id: Long,
        @Valid @RequestBody grade: Grade
    ): ResponseEntity<Unit> {
        val assessment = repository.findByIdOrNull(id)

        return assessment?.let {
            if (authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), it.education?.location?.institutionId ?: -1)) {
                grade.assessment = it
                it.grades.add(grade)
                repository.save(it)

                ResponseEntity
                        .created(URI("/grade/${grade.id}"))
                        .build()
            } else {
                ResponseEntity
                        .badRequest()
                        .build()
            }
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/assessment/{id}"],
        consumes = ["application/json"]
    )
    fun updateAssessment(
        @PathVariable("id") id: Long,
        @Valid @RequestBody assessmentProperties: AssessmentProperties
    ): ResponseEntity<Unit> {
        val assessment = repository.findByIdOrNull(id)

        return assessment?.let {
            if (authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), it.education?.location?.institutionId ?: -1)) {
                assigner.assign(assessmentProperties, it)
                repository.save(it)

                ResponseEntity
                        .ok()
                        .build()
            } else {
                ResponseEntity
                        .badRequest()
                        .build()
            }
        } ?:
            ResponseEntity
                .notFound()
                .build()

    }
}
