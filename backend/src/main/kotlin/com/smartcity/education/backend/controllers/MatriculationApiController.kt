package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.Constants
import com.smartcity.education.backend.MessageSender
import com.smartcity.education.backend.assigners.MatriculationAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.Grade
import com.smartcity.education.backend.models.Graduation
import com.smartcity.education.backend.models.MatriculationProperties
import com.smartcity.education.backend.repositories.AssessmentRepository
import com.smartcity.education.backend.repositories.MatriculationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class MatriculationApiController(
        private val repository: MatriculationRepository,
        private val assessmentRepository: AssessmentRepository,
        private val assigner: MatriculationAssigner,
        private val authUtil: AuthUtil,
        private val sender: MessageSender
) {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/matriculation/{id}/grade"],
        produces = ["application/json"]
    )
    fun allGradesOfMatriculation(
        @PathVariable("id") id: Long
    ): ResponseEntity<List<Grade>> {
        val matriculation = repository.findByIdOrNull(id)

        return matriculation?.let {
            if (authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), it.education?.location?.institutionId ?: -1)) {
                ResponseEntity
                        .ok(matriculation.grades)
            } else if (authUtil.checkUser(SecurityContextHolder.getContext()) {it.student != null}) {
                val user = authUtil.getUser(SecurityContextHolder.getContext())
                ResponseEntity
                        .ok(matriculation.grades.filter { it.matriculation?.studentId == user.student?.id })
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
        method = [RequestMethod.GET],
        value = ["/matriculation/{id}/graduation"],
        produces = ["application/json"]
    )
    fun allGraduationsOfMatriculation(
        @PathVariable("id") id: Long
    ): ResponseEntity<List<Graduation>> {
        val matriculation = repository.findByIdOrNull(id)

        return matriculation?.let {
            ResponseEntity
                    .ok(it.graduations)
        } ?:
            ResponseEntity
                    .notFound()
                    .build()
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/matriculation/{id}/grade"],
        consumes = ["application/json"]
    )
    fun createGradeOfMatriculation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody grade: Grade
    ): ResponseEntity<Unit> {
        val matriculation = repository.findByIdOrNull(id)

        return matriculation?.let {
            if (authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), it.education?.location?.institutionId ?: -1)) {
                grade.assessment = assessmentRepository.findByIdOrNull(grade.assessmentId!!)
                grade.matriculation = it
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
        method = [RequestMethod.POST],
        value = ["/matriculation/{id}/graduation"],
        consumes = ["application/json"]
    )
    fun createGraduationOfMatriculation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody graduation: Graduation
    ): ResponseEntity<Unit> {
        val matriculation = repository.findByIdOrNull(id)

        return matriculation?.let {
            if (authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), it.education?.location?.institutionId ?: -1)) {
                graduation.matriculation = it
                it.graduations.add(graduation)
                val created = repository.save(it)

                created.graduations.last().id?.let { id ->
                    sender.send(
                            Constants.RoutingKeys.graduated,
                            id.toString()
                    )
                }


                ResponseEntity
                        .created(URI("/graduation/${graduation.id}"))
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
        value = ["/matriculation/{id}"],
        consumes = ["application/json"]
    )
    fun updateMatriculation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody matriculationProperties: MatriculationProperties
    ): ResponseEntity<Unit> {
        val matriculation = repository.findByIdOrNull(id)

        return matriculation?.let {
            if (authUtil.hasInstitutionAuthority(SecurityContextHolder.getContext(), it.education?.location?.institutionId ?: -1)) {
                assigner.assign(matriculationProperties, it)
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
