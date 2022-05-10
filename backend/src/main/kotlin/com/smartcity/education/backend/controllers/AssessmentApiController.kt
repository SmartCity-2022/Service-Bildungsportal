package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.models.AssessmentProperties
import com.smartcity.education.backend.models.Grade
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class AssessmentApiController {


    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/assessment/{id}/grade"],
        produces = ["application/json"]
    )
    fun allGradesOfAssessment( @PathVariable("id") id: Int
): ResponseEntity<List<Grade>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }


    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/assessment/{id}/grade"],
        consumes = ["application/json"]
    )
    fun createGradeOfAssessment( @PathVariable("id") id: Int
, @Valid @RequestBody grade: Grade
): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }


    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/assessment/{id}"],
        consumes = ["application/json"]
    )
    fun updateAssessment( @PathVariable("id") id: Int
, @Valid @RequestBody assessmentProperties: AssessmentProperties
): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
