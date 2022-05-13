package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.models.GraduationProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated

import javax.validation.Valid

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class GraduationApiController {
    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/graduation/{id}"],
        consumes = ["application/json"]
    )
    fun updateGraduation(
        @PathVariable("id") id: Int,
        @Valid @RequestBody graduationProperties: GraduationProperties
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
