package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.GraduationAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.*
import com.smartcity.education.backend.repositories.GraduationRepository
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

@SpringBootTest(classes = [GraduationApiController::class])
class GraduationApiControllerTests {
    @MockBean
    private val repository: GraduationRepository? = null
    @MockBean
    private val assigner: GraduationAssigner? = null
    @MockBean
    private val authUtil: AuthUtil? = null
    @Autowired
    private val sut: GraduationApiController? = null

    @Test
    fun testUpdateAssessment_DoesNotExist() {
        val id = 0L
        val properties = GraduationProperties()

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.updateGraduation(id, properties)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testUpdateGrade_DoesExist() {
        val id = 0L
        val properties = GraduationProperties()
        val obj = Graduation(
            date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.updateGraduation(id, properties)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(assigner, times(1))?.assign(properties, obj)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testUpdateGrade_NoAuthority() {
        val id = 0L
        val properties = GraduationProperties()
        val obj = Graduation(
                date = LocalDateTime.now()
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.updateGraduation(id, properties)
        Assertions.assertEquals(result?.statusCode, HttpStatus.BAD_REQUEST)

        verify(repository, times(1))?.findById(id)
        verify(authUtil, times(1))?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)
        verify(assigner, never())?.assign(properties, obj)
        verify(repository, never())?.save(obj)
    }
}