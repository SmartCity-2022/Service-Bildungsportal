package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.models.Assessment
import com.smartcity.education.backend.models.EducationProperties
import com.smartcity.education.backend.models.Matriculation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class EducationApiController {
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/education/{id}/qualification"],
        consumes = ["application/json"]
    )
    fun addQualificationsOfEducation(
        @PathVariable("id") id: Int,
        @Valid @RequestBody requestBody: List<Int>
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/education/{id}/assessment"],
        produces = ["application/json"]
    )
    fun allAssessmentsOfEducation(
        @PathVariable("id") id: Int
    ): ResponseEntity<List<Assessment>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/education/{id}/matriculation"],
        produces = ["application/json"]
    )
    fun allMatriculationsOfEducation(
        @PathVariable("id") id: Int
    ): ResponseEntity<List<Matriculation>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/education/{id}/qualification"],
        produces = ["application/json"]
    )
    fun allQualificationsOfEducation(
        @PathVariable("id") id: Int
    ): ResponseEntity<List<Int>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/education/{id}/assessment"],
        consumes = ["application/json"]
    )
    fun createAssessmentOfEducation(
        @PathVariable("id") id: Int,
        @Valid @RequestBody assessment: Assessment
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/education/{id}/matriculation"],
        consumes = ["application/json"]
    )
    fun createMatriculationOfEducation(
        @PathVariable("id") id: Int,
        @Valid @RequestBody matriculation: Matriculation
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/education/{educationId}/qualification/{qualificationId}"]
    )
    fun removeQualificationsOfEducation(
        @PathVariable("educationId") educationId: Int,
        @PathVariable("qualificationId") qualificationId: Int
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/education/{id}"],
        consumes = ["application/json"]
    )
    fun updateEducationOfLocation(
        @PathVariable("id") id: Int,
        @Valid @RequestBody educationProperties: EducationProperties
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
