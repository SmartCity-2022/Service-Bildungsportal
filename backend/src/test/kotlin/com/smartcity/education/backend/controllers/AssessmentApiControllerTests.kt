package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.AssessmentAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.*
import com.smartcity.education.backend.repositories.AssessmentRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime
import java.util.*

@SpringBootTest(classes = [AssessmentApiController::class])
class AssessmentApiControllerTests {
    @MockBean
    private val repository: AssessmentRepository? = null
    @MockBean
    private val assigner: AssessmentAssigner? = null
    @MockBean
    private val authUtil: AuthUtil? = null
    @Autowired
    private val sut: AssessmentApiController? = null

    @Test
    fun testAllGradesOfAssessment_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.allGradesOfAssessment(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllGradesOfAssessment_DoesExist() {
        val id = 0L
        val grades = mutableListOf(
                Grade(
                        grade = 1.0f,
                        date = LocalDateTime.now()
                )
        )
        val obj = Assessment(
            title = "",
            grades = grades
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.allGradesOfAssessment(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, grades)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
    }

    @Test
    fun testAllGradesOfAssessment_NoAuthority() {
        val id = 0L
        val obj = Assessment(
                title = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.allGradesOfAssessment(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.BAD_REQUEST)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
    }

    @Test
    fun testCreateGradeOfAssessment_DoesNotExist() {
        val id = 0L
        val grade = Grade(
                grade = 1.0f,
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.createGradeOfAssessment(id, grade)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testCreateGradeOfAssessment_DoesExist() {
        val id = 0L
        val grade = Grade(
                grade = 1.0f,
                date = LocalDateTime.now()
        )
        val obj = Assessment(
                title = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.createGradeOfAssessment(id, grade)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testCreateGradeOfAssessment_NoAuthority() {
        val id = 0L
        val grade = Grade(
                grade = 1.0f,
                date = LocalDateTime.now()
        )
        val obj = Assessment(
                title = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.createGradeOfAssessment(id, grade)
        Assertions.assertEquals(result?.statusCode, HttpStatus.BAD_REQUEST)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(repository, never())?.save(obj)
    }

    @Test
    fun testUpdateAssessment_DoesNotExist() {
        val id = 0L
        val properties = AssessmentProperties()

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.updateAssessment(id, properties)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testUpdateAssessment_DoesExist() {
        val id = 0L
        val properties = AssessmentProperties()
        val obj = Assessment(
                title = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.updateAssessment(id, properties)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(assigner, times(1))?.assign(properties, obj)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testUpdateAssessment_NoAuthority() {
        val id = 0L
        val properties = AssessmentProperties()
        val obj = Assessment(
                title = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.updateAssessment(id, properties)
        Assertions.assertEquals(result?.statusCode, HttpStatus.BAD_REQUEST)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(assigner, never())?.assign(properties, obj)
        verify(repository, never())?.save(obj)
    }
}