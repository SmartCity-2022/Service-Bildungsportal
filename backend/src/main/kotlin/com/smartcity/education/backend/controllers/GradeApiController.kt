package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.models.GradeProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated

import javax.validation.Valid

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class GradeApiController {
    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/grade/{id}"],
        consumes = ["application/json"]
    )
    fun updateGrade(
        @PathVariable("id") id: Int,
        @Valid @RequestBody gradeProperties: GradeProperties
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
