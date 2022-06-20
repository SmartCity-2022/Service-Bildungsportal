package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.MatriculationAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.*
import com.smartcity.education.backend.repositories.AssessmentRepository
import com.smartcity.education.backend.repositories.MatriculationRepository
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

@SpringBootTest(classes = [MatriculationApiController::class])
class MatriculationApiControllerTests {
    @MockBean
    private val repository: MatriculationRepository? = null
    @MockBean
    private val assessmentRepository: AssessmentRepository? = null
    @MockBean
    private val assigner: MatriculationAssigner? = null
    @MockBean
    private val authUtil: AuthUtil? = null
    @Autowired
    private val sut: MatriculationApiController? = null

    @Test
    fun testAllGradesOfMatriculation_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.allGradesOfMatriculation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllGradesOfMatriculation_DoesExist() {
        val id = 0L
        val grades = mutableListOf(
                Grade(
                        grade = 1.0f,
                        date = LocalDateTime.now()
                )
        )
        val obj = Matriculation(
            date = LocalDateTime.now(),
            grades = grades
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.allGradesOfMatriculation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, grades)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
    }

    @Test
    fun testAllGradesOfMatriculation_NoAuthority() {
        val id = 0L
        val obj = Matriculation(
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.allGradesOfMatriculation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.BAD_REQUEST)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
    }

    @Test
    fun testAllGraduationsOfMatriculation_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.allGraduationsOfMatriculation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllGraduationsOfMatriculation_DoesExist() {
        val id = 0L
        val graduations = mutableListOf(
                Graduation(
                        date = LocalDateTime.now()
                )
        )
        val obj = Matriculation(
                date = LocalDateTime.now(),
                graduations = graduations
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.allGraduationsOfMatriculation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, graduations)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testCreateGradeOfMatriculation_DoesNotExist() {
        val id = 0L
        val grade = Grade(
                grade = 1.0f,
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.createGradeOfMatriculation(id, grade)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testCreateGradeOfMatriculation_DoesExist() {
        val id = 0L
        val grade = Grade(
                grade = 1.0f,
                date = LocalDateTime.now()
        )
        val assessmentId = 1L
        grade.assessmentId = assessmentId
        val obj = Matriculation(
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.createGradeOfMatriculation(id, grade)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.findById(id)
        verify(assessmentRepository, times(1))?.findById(assessmentId)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testCreateGradeOfMatriculation_NoAuthority() {
        val id = 0L
        val grade = Grade(
                grade = 1.0f,
                date = LocalDateTime.now()
        )
        val obj = Matriculation(
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.createGradeOfMatriculation(id, grade)
        Assertions.assertEquals(result?.statusCode, HttpStatus.BAD_REQUEST)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(repository, never())?.save(obj)
    }

    @Test
    fun testCreateGraduationOfMatriculation_DoesNotExist() {
        val id = 0L
        val graduation = Graduation(
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.createGraduationOfMatriculation(id, graduation)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testCreateGraduationOfMatriculation_DoesExist() {
        val id = 0L
        val graduation = Graduation(
                date = LocalDateTime.now()
        )
        val obj = Matriculation(
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.createGraduationOfMatriculation(id, graduation)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testCreateGraduationOfMatriculation_NoAuthority() {
        val id = 0L
        val graduation = Graduation(
                date = LocalDateTime.now()
        )
        val obj = Matriculation(
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.createGraduationOfMatriculation(id, graduation)
        Assertions.assertEquals(result?.statusCode, HttpStatus.BAD_REQUEST)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(repository, never())?.save(obj)
    }

    @Test
    fun testUpdateMatriculation_DoesNotExist() {
        val id = 0L
        val properties = MatriculationProperties()

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.updateMatriculation(id, properties)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testUpdateMatriculation_DoesExist() {
        val id = 0L
        val properties = MatriculationProperties()
        val obj = Matriculation(
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.updateMatriculation(id, properties)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(assigner, times(1))?.assign(properties, obj)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testUpdateMatriculation_NoAuthority() {
        val id = 0L
        val properties = MatriculationProperties()
        val obj = Matriculation(
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.updateMatriculation(id, properties)
        Assertions.assertEquals(result?.statusCode, HttpStatus.BAD_REQUEST)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(assigner, never())?.assign(properties, obj)
        verify(repository, never())?.save(obj)
    }
}