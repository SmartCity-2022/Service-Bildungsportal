package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.models.Grade
import com.smartcity.education.backend.models.Graduation
import com.smartcity.education.backend.models.MatriculationProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class MatriculationApiController {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/matriculation/{id}/grade"],
        produces = ["application/json"]
    )
    fun allGradesOfMatriculation(
        @PathVariable("id") id: Int
    ): ResponseEntity<List<Grade>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/matriculation/{id}/graduation"],
        produces = ["application/json"]
    )
    fun allGraduationsOfMatriculation(
        @PathVariable("id") id: Int
    ): ResponseEntity<List<Graduation>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/matriculation/{id}/grade"],
        consumes = ["application/json"]
    )
    fun createGradeOfMatriculation(
        @PathVariable("id") id: Int,
        @Valid @RequestBody grade: Grade
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/matriculation/{id}/graduation"],
        consumes = ["application/json"]
    )
    fun createGraduationOfMatriculation(
        @PathVariable("id") id: Int,
        @Valid @RequestBody graduation: Graduation
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/matriculation/{id}"],
        consumes = ["application/json"]
    )
    fun updateMatriculation(
        @PathVariable("id") id: Int,
        @Valid @RequestBody matriculationProperties: MatriculationProperties
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
