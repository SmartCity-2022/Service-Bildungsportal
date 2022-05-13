package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.InstitutionAssigner
import com.smartcity.education.backend.models.Institution
import com.smartcity.education.backend.models.InstitutionProperties
import com.smartcity.education.backend.models.Location
import com.smartcity.education.backend.repositories.InstitutionRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.test.context.web.WebAppConfiguration
import java.util.*
import kotlin.collections.HashSet

@SpringBootTest(classes = [InstitutionApiController::class])
class InstitutionApiControllerTests {
    @MockBean
    private val repository: InstitutionRepository? = null

    @MockBean
    private val assigner: InstitutionAssigner? = null

    @Autowired
    private val sut: InstitutionApiController? = null

    @Test
    fun testAllInstitutions() {
        sut?.allInstitutions()
        verify(repository, times(1))?.findAll()
    }

    @Test
    fun testAllLocationOfInstitution_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.allLocationsOfInstitution(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllLocationOfInstitution_DoesExist() {
        val obj = Institution(
            name = ""
        )
        obj.locations.add(Location(
            address = "",
            city = "",
            zip = ""
        ))
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.allLocationsOfInstitution(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj.locations)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testCreateInstitution() {
        val obj = Institution(
            name = ""
        )

        val result = sut?.createInstitution(obj)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testCreateLocationOfInstitution_DoesNotExist() {
        val id = 0L
        val location = Location(
            address = "",
            city = "",
            zip = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.createLocationOfInstitution(id, location)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testCreateLocationOfInstitution_DoesExist() {
        val obj = Institution(
            name = ""
        )
        val location = Location(
            address = "",
            city = "",
            zip = ""
        )
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.createLocationOfInstitution(id, location)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.findById(id)
        verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testSingleInstitution_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.singleInstitution(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testSingleInstitution_DoesExist() {
        val obj = Institution(
            name = ""
        )
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.singleInstitution(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testUpdateInstitution_DoesNotExist() {
        val prop = InstitutionProperties()
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.updateInstitution(id, prop)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testUpdateInstitution_DoesExist() {
        val obj = Institution(
            name = ""
        )
        val prop = InstitutionProperties()
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.updateInstitution(id, prop)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)

        val inOrder = inOrder(repository, assigner)
        inOrder.verify(repository, times(1))?.findById(id)
        inOrder.verify(assigner, times(1))?.assign(prop, obj)
        inOrder.verify(repository, times(1))?.save(obj)
    }
}