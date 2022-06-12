package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.Matriculation
import com.smartcity.education.backend.models.User
import com.smartcity.education.backend.repositories.MatriculationRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class MeApiController(
    private val authUtil: AuthUtil,
    private val matriculationRepository: MatriculationRepository
) {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/me"],
        produces = ["application/json"]
    )
    fun me(): ResponseEntity<User> {
        return ResponseEntity.ok(authUtil.getUser(SecurityContextHolder.getContext()))
    }

    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/me/matriculation"],
            produces = ["application/json"]
    )
    fun myMatriculations(): ResponseEntity<Iterable<Matriculation>> {
        val user = authUtil.getUser(SecurityContextHolder.getContext())

        return user.student?.id?.let {
            val matriculations = matriculationRepository.findMatriculationsByStudent_Id(it)

            ResponseEntity
                    .ok(matriculations)
        } ?:
            ResponseEntity
                    .badRequest()
                    .build()
    }
}
