package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.QualificationAssigner
import com.smartcity.education.backend.models.Education
import com.smartcity.education.backend.models.Qualification
import com.smartcity.education.backend.models.QualificationProperties
import com.smartcity.education.backend.repositories.QualificationRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import java.util.*

@SpringBootTest(classes = [QualificationApiController::class])
class QualificationApiControllerTests {
    @MockBean
    private val repository: QualificationRepository? = null

    @MockBean
    private val assigner: QualificationAssigner? = null

    @Autowired
    private val sut: QualificationApiController? = null

    @Test
    fun testAddEducationsOfQualification_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.addEducationsOfQualification(id, emptyList())
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAddEducationsOfQualification_DoesExist() {
        val id = 0L
        val obj = Qualification(
            name = "",
            description = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.addEducationsOfQualification(id, emptyList())
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllEducationsOfQualification_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.allEducationsOfQualification(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllEducationsOfQualification_DoesExist() {
        val id = 0L
        val obj = Qualification(
            name = "",
            description = ""
        )
        obj.educations.add(
            Education(
            title = "",
            description = ""
        ))

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.allEducationsOfQualification(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj.educations.map { it.id ?: 0 })

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllQualifications() {
        val result = sut?.allQualifications()
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        verify(repository, times(1))?.findAll()
    }

    @Test
    fun testCreateStudent() {
        val obj = Qualification(
            name = "",
            description = ""
        )

        val result = sut?.createQualification(obj)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testRemoveEducationsOfQualification_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.removeEducationsOfQualification(id, 0)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testRemoveEducationsOfQualification_DoesExist() {
        val id = 0L
        val obj = Qualification(
            name = "",
            description = ""
        )
        val educationId = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.removeEducationsOfQualification(id, educationId)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)

        verify(repository, times(1))?.findById(id)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testSingleQualification_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.singleQualification(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testSingleQualification_DoesExist() {
        val obj = Qualification(
            name = "",
            description = ""
        )
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.singleQualification(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testUpdateQualification_DoesNotExist() {
        val prop = QualificationProperties()
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.updateQualification(id, prop)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testUpdateQualification_DoesExist() {
        val obj = Qualification(
            name = "",
            description = ""
        )
        val prop = QualificationProperties()
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.updateQualification(id, prop)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)

        val inOrder = inOrder(repository, assigner)
        inOrder.verify(repository, times(1))?.findById(id)
        inOrder.verify(assigner, times(1))?.assign(prop, obj)
        inOrder.verify(repository, times(1))?.save(obj)
    }
}