package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.MessageSender
import com.smartcity.education.backend.assigners.EducationAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.*
import com.smartcity.education.backend.repositories.EducationRepository
import com.smartcity.education.backend.repositories.QualificationRepository
import com.smartcity.education.backend.repositories.StudentRepository
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

@SpringBootTest(classes = [EducationApiController::class])
class EducationApiControllerTests {
    @MockBean
    private val repository: EducationRepository? = null
    @MockBean
    private val qualificationRepository: QualificationRepository? = null
    @MockBean
    private val studentRepository: StudentRepository? = null
    @MockBean
    private val authUtil: AuthUtil? = null
    @MockBean
    private val assigner: EducationAssigner? = null
    @MockBean
    private val sender: MessageSender? = null
    @Autowired
    private val sut: EducationApiController? = null

    @Test
    fun testAddQualificationsOfEducation_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.addQualificationsOfEducation(id, emptyList())
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(Mockito.any())
    }

    @Test
    fun testAddQualificationsOfEducation_DoesExist() {
        val id = 0L
        val obj = Education(
            title = "",
            description = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.addQualificationsOfEducation(id, emptyList())
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.findById(id)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testAddQualificationsOfEducation_NoAuthority() {
        val id = 0L
        val obj = Education(
                title = "",
                description = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.addQualificationsOfEducation(id, emptyList())
        Assertions.assertEquals(result?.statusCode, HttpStatus.FORBIDDEN)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(obj)
    }

    @Test
    fun testAllAssessmentsOfEducation_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.allAssessmentsOfEducation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllAssessmentsOfEducation_DoesExist() {
        val id = 0L
        val obj = Education(
            title = "",
            description = ""
        )
        obj.assessments.add(Assessment(
            title = ""
        ))

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.allAssessmentsOfEducation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj.assessments)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllMatriculationsOfEducation_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.allMatriculationsOfEducation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllMatriculationsOfEducation_DoesExist() {
        val id = 0L
        val obj = Education(
            title = "",
            description = ""
        )
        obj.matriculations.add(Matriculation(
            date = LocalDateTime.now()
        ))

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.allMatriculationsOfEducation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj.matriculations)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllQualificationsOfEducation_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.allQualificationsOfEducation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllQualificationsOfEducation_DoesExist() {
        val id = 0L
        val obj = Education(
            title = "",
            description = ""
        )
        obj.qualifications.add(Qualification(
            name = "",
            description = ""
        ))

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.allQualificationsOfEducation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj.qualifications.map { it.id ?: 0 })

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testCreateAssessmentOfEducation_DoesNotExist() {
        val id = 0L
        val assessment = Assessment(
            title = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.createAssessmentOfEducation(id, assessment)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testCreateAssessmentOfEducation_DoesExist() {
        val id = 0L
        val obj = Education(
            title = "",
            description = ""
        )
        val assessment = Assessment(
            title = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.createAssessmentOfEducation(id, assessment)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.findById(id)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testCreateAssessmentOfEducation_NoAuthority() {
        val id = 0L
        val obj = Education(
                title = "",
                description = ""
        )
        val assessment = Assessment(
                title = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.createAssessmentOfEducation(id, assessment)
        Assertions.assertEquals(result?.statusCode, HttpStatus.FORBIDDEN)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(obj)
    }

    @Test
    fun testCreateMatriculationOfEducation_DoesNotExist() {
        val id = 0L
        val studentId = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())
        `when`(authUtil?.hasStudentAuthority(SecurityContextHolder.getContext(), studentId)).thenReturn(true)

        val result = sut?.createMatriculationOfEducation(id, studentId)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testCreateMatriculationOfEducation_DoesExist() {
        val id = 0L
        val obj = Education(
            title = "",
            description = ""
        )
        val studentId = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(repository?.save(obj)).thenReturn(obj)
        `when`(authUtil?.hasStudentAuthority(SecurityContextHolder.getContext(), studentId)).thenReturn(true)

        val result = sut?.createMatriculationOfEducation(id, studentId)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.findById(id)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testCreateMatriculationOfEducation_NoAuthority() {
        val id = 0L
        val obj = Education(
                title = "",
                description = ""
        )
        val studentId = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasStudentAuthority(SecurityContextHolder.getContext(), studentId)).thenReturn(false)

        val result = sut?.createMatriculationOfEducation(id, studentId)
        Assertions.assertEquals(result?.statusCode, HttpStatus.FORBIDDEN)

        verify(repository, never())?.findById(id)
        verify(repository, never())?.save(obj)
    }

    @Test
    fun testRemoveQualificationsOfEducation_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.addQualificationsOfEducation(id, emptyList())
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testRemoveQualificationsOfEducation_DoesExist() {
        val id = 0L
        val obj = Education(
            title = "",
            description = ""
        )
        val qualificationId = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.removeQualificationsOfEducation(id, qualificationId)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)

        verify(repository, times(1))?.findById(id)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testRemoveQualificationsOfEducation_NoAuthority() {
        val id = 0L
        val obj = Education(
                title = "",
                description = ""
        )
        val qualificationId = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.removeQualificationsOfEducation(id, qualificationId)
        Assertions.assertEquals(result?.statusCode, HttpStatus.FORBIDDEN)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(obj)
    }

    @Test
    fun testSingleEducation_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.singleEducation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testSingleEducation_DoesExist() {
        val id = 0L
        val obj = Education(
            title = "",
            description = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.singleEducation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testUpdateEducationOfLocation_DoesNotExist() {
        val id = 0L
        val prop = EducationProperties()

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.updateEducationOfLocation(id, prop)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testUpdateEducationOfLocation_DoesExist() {
        val id = 0L
        val obj = Education(
            title = "",
            description = ""
        )
        val prop = EducationProperties()

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.updateEducationOfLocation(id, prop)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)

        val inOrder = inOrder(repository, assigner)
        inOrder.verify(repository, times(1))?.findById(id)
        inOrder.verify(assigner, times(1))?.assign(prop, obj)
        inOrder.verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testUpdateEducationOfLocation_NoAuthority() {
        val id = 0L
        val obj = Education(
                title = "",
                description = ""
        )
        val prop = EducationProperties()

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.updateEducationOfLocation(id, prop)
        Assertions.assertEquals(result?.statusCode, HttpStatus.FORBIDDEN)

        val inOrder = inOrder(repository, assigner)
        inOrder.verify(repository, times(1))?.findById(id)
        inOrder.verify(assigner, never())?.assign(prop, obj)
        inOrder.verify(repository, never())?.save(obj)
    }
}