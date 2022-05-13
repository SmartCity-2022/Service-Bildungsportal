package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.models.Education
import com.smartcity.education.backend.models.LocationProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class LocationApiController {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/location/{id}/education"],
        produces = ["application/json"]
    )
    fun allEducationsOfLocation(
        @PathVariable("id") id: Int
    ): ResponseEntity<List<Education>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/location/{id}/education"],
        consumes = ["application/json"]
    )
    fun createEducationOfLocation(
        @PathVariable("id") id: Int,
        @Valid @RequestBody education: Education
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/location/{id}"],
        consumes = ["application/json"]
    )
    fun updateLocationOfInstitution(
        @PathVariable("id") id: Int,
        @Valid @RequestBody locationProperties: LocationProperties
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
