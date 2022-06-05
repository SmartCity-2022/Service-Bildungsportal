package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.Constants
import com.smartcity.education.backend.assigners.StudentAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.Matriculation
import com.smartcity.education.backend.models.Student
import com.smartcity.education.backend.models.StudentProperties
import com.smartcity.education.backend.repositories.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import java.net.URI

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class StudentApiController(private val authUtil: AuthUtil) {
    @Autowired
    private val repository: StudentRepository? = null
    @Autowired
    private val assigner: StudentAssigner? = null

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/student/{id}/matriculation"],
        produces = ["application/json"]
    )
    fun allMatriculationsOfStudent(
        @PathVariable("id") id: Long
    ): ResponseEntity<List<Matriculation>> {
        val student = repository?.findByIdOrNull(id)

        return student?.let {
            ResponseEntity
                .ok(it.matriculations)
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/student"],
        produces = ["application/json"]
    )
    fun allStudents(): ResponseEntity<Iterable<Student>> {
        val students = repository?.findAll()

        return ResponseEntity
            .ok(students)
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/student/{id}/matriculation"],
        consumes = ["application/json"]
    )
    fun createMatriculationOfStudent(
        @PathVariable("id") id: Long,
        @Valid @RequestBody matriculation: Matriculation
    ): ResponseEntity<Unit> {
        if (!authUtil.hasStudentAuthority(SecurityContextHolder.getContext(), id)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build()
        }

        val student = repository?.findByIdOrNull(id)

        return student?.let {
            matriculation.student = it
            it.matriculations.add(matriculation)
            repository?.save(it)

            ResponseEntity
                .created(URI("/matriculation/${matriculation.id}"))
                .build()
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/student"],
        consumes = ["application/json"]
    )
    fun createStudent(
        @Valid @RequestBody student: Student
    ): ResponseEntity<Unit> {
        val context = SecurityContextHolder.getContext()
        if (authUtil.hasAuthority(context, Constants.Authorities.student)) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        repository?.save(student)
        authUtil.updateUser(context) { user ->
            user.student = student
        }

        return ResponseEntity
            .created(URI("/student/${student.id}"))
            .build()
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/student/{id}"],
        produces = ["application/json"]
    )
    fun singleStudent(
        @PathVariable("id") id: Long
    ): ResponseEntity<Student> {
        val student = repository?.findByIdOrNull(id)

        return student?.let {
            ResponseEntity
                .ok(it)
        } ?:
            ResponseEntity
               .notFound()
               .build()
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/student/{id}"],
        consumes = ["application/json"]
    )
    fun updateStudent(
        @PathVariable("id") id: Long,
        @Valid @RequestBody studentProperties: StudentProperties
    ): ResponseEntity<Unit> {
        if (!authUtil.hasStudentAuthority(SecurityContextHolder.getContext(), id)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build()
        }

        val student = repository?.findByIdOrNull(id)

        return student?.let {
            assigner?.assign(studentProperties, it)
            repository?.save(it)
            
            ResponseEntity
                .ok()
                .build()
        } ?:
        ResponseEntity
            .notFound()
            .build()
    }
}
