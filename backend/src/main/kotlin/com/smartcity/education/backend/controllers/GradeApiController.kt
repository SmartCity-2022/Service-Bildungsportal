package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.GradeAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.GradeProperties
import com.smartcity.education.backend.repositories.GradeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated

import javax.validation.Valid

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class GradeApiController(
        private val repository: GradeRepository,
        private val assigner: GradeAssigner,
        private val authUtil: AuthUtil
) {
    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/grade/{id}"],
        consumes = ["application/json"]
    )
    fun updateGrade(
        @PathVariable("id") id: Long,
        @Valid @RequestBody gradeProperties: GradeProperties
    ): ResponseEntity<Unit> {
        val grade = repository.findByIdOrNull(id)

        return grade?.let {
            if (authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), it.assessment?.education?.location?.institutionId ?: -1)) {
                assigner.assign(gradeProperties, it)
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
