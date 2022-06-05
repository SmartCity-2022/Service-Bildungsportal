package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.Constants
import com.smartcity.education.backend.assigners.StudentAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.*
import com.smartcity.education.backend.repositories.StudentRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime
import java.util.*

@SpringBootTest(classes = [StudentApiController::class])
class StudentApiControllerTests {
    @MockBean
    private val repository: StudentRepository? = null

    @MockBean
    private val assigner: StudentAssigner? = null

    @MockBean
    private val authUtil: AuthUtil? = null

    @Autowired
    private val sut: StudentApiController? = null

    @Test
    fun testAllMatriculationsOfStudent_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.allMatriculationsOfStudent(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllMatriculationsOfStudent_DoesExist() {
        val obj = Student(
            name = ""
        )
        obj.matriculations.add(Matriculation(
            date = LocalDateTime.now()
        ))
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.allMatriculationsOfStudent(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj.matriculations)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllStudents() {
        val result = sut?.allStudents()
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        verify(repository, times(1))?.findAll()
    }

    @Test
    fun testCreateMatriculationOfStudent_DoesNotExist() {
        val id = 0L
        val matriculation = Matriculation(
            date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.empty())
        `when`(authUtil?.hasStudentAuthority(SecurityContextHolder.getContext(), id)).thenReturn(true)

        val result = sut?.createMatriculationOfStudent(id, matriculation)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testCreateMatriculationOfStudent_DoesExist() {
        val obj = Student(
            name = ""
        )
        val matriculation = Matriculation(
            date = LocalDateTime.now()
        )
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasStudentAuthority(SecurityContextHolder.getContext(), id)).thenReturn(true)

        val result = sut?.createMatriculationOfStudent(id, matriculation)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.findById(id)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testCreateMatriculationOfStudent_NoAuthority() {
        val obj = Student(
                name = ""
        )
        val matriculation = Matriculation(
                date = LocalDateTime.now()
        )
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasStudentAuthority(SecurityContextHolder.getContext(), id)).thenReturn(false)

        val result = sut?.createMatriculationOfStudent(id, matriculation)
        Assertions.assertEquals(result?.statusCode, HttpStatus.FORBIDDEN)

        verify(repository, never())?.findById(id)
        verify(repository, never())?.save(obj)
    }

    @Test
    fun testCreateStudent() {
        val obj = Student(
            name = ""
        )

        val result = sut?.createStudent(obj)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testCreateStudent_AlreadyExists() {
        val obj = Student(
                name = ""
        )

        `when`(authUtil?.hasAuthority(SecurityContextHolder.getContext(), Constants.Authorities.student)).thenReturn(true)

        val result = sut?.createStudent(obj)
        Assertions.assertEquals(result?.statusCode, HttpStatus.BAD_REQUEST)

        verify(repository, never())?.save(obj)
    }

    @Test
    fun testSingleStudent_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.singleStudent(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testSingleStudent_DoesExist() {
        val obj = Student(
            name = ""
        )
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.singleStudent(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testUpdateStudent_DoesNotExist() {
        val prop = StudentProperties()
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())
        `when`(authUtil?.hasStudentAuthority(SecurityContextHolder.getContext(), id)).thenReturn(true)

        val result = sut?.updateStudent(id, prop)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testUpdateStudent_DoesExist() {
        val obj = Student(
            name = ""
        )
        val prop = StudentProperties()
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasStudentAuthority(SecurityContextHolder.getContext(), id)).thenReturn(true)

        val result = sut?.updateStudent(id, prop)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)

        val inOrder = inOrder(repository, assigner)
        inOrder.verify(repository, times(1))?.findById(id)
        inOrder.verify(assigner, times(1))?.assign(prop, obj)
        inOrder.verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testUpdateStudent_NoAuthority() {
        val obj = Student(
                name = ""
        )
        val prop = StudentProperties()
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasStudentAuthority(SecurityContextHolder.getContext(), id)).thenReturn(false)

        val result = sut?.updateStudent(id, prop)
        Assertions.assertEquals(result?.statusCode, HttpStatus.FORBIDDEN)

        val inOrder = inOrder(repository, assigner)
        inOrder.verify(repository, never())?.findById(id)
        inOrder.verify(assigner, never())?.assign(prop, obj)
        inOrder.verify(repository, never())?.save(obj)
    }
}