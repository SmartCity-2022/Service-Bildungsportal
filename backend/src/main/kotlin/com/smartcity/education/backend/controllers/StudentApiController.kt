package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.models.Matriculation
import com.smartcity.education.backend.models.Student
import com.smartcity.education.backend.models.StudentProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class StudentApiController {
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/student/{id}/matriculation"],
        produces = ["application/json"]
    )
    fun allMatriculationsOfStudent(
        @PathVariable("id") id: Int
    ): ResponseEntity<List<Matriculation>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/student"],
        produces = ["application/json"]
    )
    fun allStudents(): ResponseEntity<List<Student>> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/student/{id}/matriculation"],
        consumes = ["application/json"]
    )
    fun createMatriculationOfStudent(
        @PathVariable("id") id: Int,
        @Valid @RequestBody matriculation: Matriculation
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/student"],
        consumes = ["application/json"]
    )
    fun createStudent(
        @Valid @RequestBody student: Student
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/student/{id}"],
        produces = ["application/json"]
    )
    fun singleStudent(
        @PathVariable("id") id: Int
    ): ResponseEntity<Student> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/student/{id}"],
        consumes = ["application/json"]
    )
    fun updateStudent(
        @PathVariable("id") id: Int,
        @Valid @RequestBody studentProperties: StudentProperties
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    }
}
