package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.models.Qualification
import com.smartcity.education.backend.models.QualificationProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class QualificationApiController {
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/qualification/{id}/education"],
        consumes = ["application/json"]
    )
    fun addEducationsOfQualification(
        @PathVariable("id") id: Int,
        @Valid @RequestBody requestBody: List<Int>
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/qualification/{id}/education"],
        produces = ["application/json"]
    )
    fun allEducationsOfQualification(
        @PathVariable("id") id: Int
    ): ResponseEntity<List<Int>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/qualification"],
        produces = ["application/json"]
    )
    fun allQualifications(): ResponseEntity<List<Qualification>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/qualification"],
        consumes = ["application/json"]
    )
    fun createQualification(
        @Valid @RequestBody qualification: Qualification
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/qualification/{qualificationId}/education/{educationId}"]
    )
    fun removeEducationsOfQualification(
        @PathVariable("qualificationId") qualificationId: Int,
        @PathVariable("educationId") educationId: Int
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/qualification/{id}"],
        produces = ["application/json"]
    )
    fun singleQualification(
        @PathVariable("id") id: Int
    ): ResponseEntity<Qualification> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/qualification/{id}"],
        consumes = ["application/json"]
    )
    fun updateQualification(
        @PathVariable("id") id: Int,
        @Valid @RequestBody qualificationProperties: QualificationProperties
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
