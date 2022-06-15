package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.GraduationAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.GraduationProperties
import com.smartcity.education.backend.repositories.GraduationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class GraduationApiController(
        private val repository: GraduationRepository,
        private val assigner: GraduationAssigner,
        private val authUtil: AuthUtil
) {
    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/graduation/{id}"],
        consumes = ["application/json"]
    )
    fun updateGraduation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody graduationProperties: GraduationProperties
    ): ResponseEntity<Unit> {
        val graduation = repository.findByIdOrNull(id)

        return graduation?.let {
            if (authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), it.matriculation?.education?.location?.institutionId ?: -1)) {
                assigner.assign(graduationProperties, it)
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
