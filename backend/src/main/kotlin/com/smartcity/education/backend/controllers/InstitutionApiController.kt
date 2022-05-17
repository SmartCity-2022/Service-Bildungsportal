package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.models.Institution
import com.smartcity.education.backend.models.InstitutionProperties
import com.smartcity.education.backend.models.Location
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class InstitutionApiController {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/institution"],
        produces = ["application/json"]
    )
    fun allInstitutions(): ResponseEntity<List<Institution>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/institution/{id}/location"],
        produces = ["application/json"]
    )
    fun allLocationsOfInstitution(
        @PathVariable("id") id: Int
    ): ResponseEntity<List<Location>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/institution"],
        consumes = ["application/json"]
    )
    fun createInstitution(
        @Valid @RequestBody institution: Institution
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/institution/{id}/location"],
        consumes = ["application/json"]
    )
    fun createLocationOfInstitution(
        @PathVariable("id") id: Int,
        @Valid @RequestBody location: Location
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/institution/{id}"],
        produces = ["application/json"]
    )
    fun singleInstitution(
        @PathVariable("id") id: Int
    ): ResponseEntity<Institution> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/institution/{id}"],
        consumes = ["application/json"]
    )
    fun updateInstitution(
        @PathVariable("id") id: Int,
        @Valid @RequestBody institutionProperties: InstitutionProperties
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
