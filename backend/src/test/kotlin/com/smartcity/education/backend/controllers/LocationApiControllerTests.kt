package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.Constants
import com.smartcity.education.backend.assigners.LocationAssigner
import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.Education
import com.smartcity.education.backend.models.InstitutionProperties
import com.smartcity.education.backend.models.Location
import com.smartcity.education.backend.models.LocationProperties
import com.smartcity.education.backend.repositories.LocationRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@SpringBootTest(classes = [LocationApiController::class])
class LocationApiControllerTests {
    @MockBean
    private val repository: LocationRepository? = null

    @MockBean
    private val assigner: LocationAssigner? = null

    @MockBean
    private val template: RabbitTemplate? = null

    @MockBean
    private val authUtil: AuthUtil? = null

    @Autowired
    private val sut: LocationApiController? = null

    @Test
    fun testAllEducationsOfLocation_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.allEducationsOfLocation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testAllEducationsOfLocation_DoesExist() {
        val id = 0L
        val obj = Location(
            address = "",
            city = "",
            zip = ""
        )
        obj.educations.add(Education(
            title = "",
            description = ""
        ))

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.allEducationsOfLocation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj.educations)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testCreateEducationOfLocation_DoesNotExist() {
        val id = 0L
        val education = Education(
            title = "",
            description = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.createEducationOfLocation(id, education)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testCreateEducationOfLocation_DoesExist() {
        val id = 0L
        val educationId = 1L
        val obj = Location(
            address = "",
            city = "",
            zip = ""
        )
        val education = Education(
            id = educationId,
            title = "",
            description = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(repository?.save(obj)).thenReturn(obj)
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.createEducationOfLocation(id, education)
        Assertions.assertEquals(result?.statusCode, HttpStatus.CREATED)

        verify(repository, times(1))?.findById(id)
        verify(repository, times(1))?.save(obj)
        verify(template, times(1))?.convertAndSend(
            Constants.exchange,
            Constants.RoutingKeys.created,
            educationId.toString()
        )
    }

    @Test
    fun testCreateEducationOfLocation_NoAuthority() {
        val id = 0L
        val educationId = 1L
        val obj = Location(
                address = "",
                city = "",
                zip = ""
        )
        val education = Education(
                id = educationId,
                title = "",
                description = ""
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(repository?.save(obj)).thenReturn(obj)
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.createEducationOfLocation(id, education)
        Assertions.assertEquals(result?.statusCode, HttpStatus.FORBIDDEN)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(obj)
        verify(template, never())?.convertAndSend(
                Constants.exchange,
                Constants.RoutingKeys.created,
                educationId.toString()
        )
    }

    @Test
    fun testSingleLocation_DoesNotExist() {
        val id = 0L

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.singleLocation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testSingleLocation_DoesExist() {
        val id = 0L
        val obj = Location(
            address = "",
            zip = "",
            city = "",
        )

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))

        val result = sut?.singleLocation(id)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, obj)

        verify(repository, times(1))?.findById(id)
    }

    @Test
    fun testUpdateLocation_DoesNotExist() {
        val id = 0L
        val props = LocationProperties()

        `when`(repository?.findById(id)).thenReturn(Optional.empty())

        val result = sut?.updateLocation(id, props)
        Assertions.assertEquals(result?.statusCode, HttpStatus.NOT_FOUND)

        verify(repository, times(1))?.findById(id)
        verify(repository, never())?.save(any())
    }

    @Test
    fun testUpdateLocation_DoesExist() {
        val id = 0L
        val obj = Location(
            address = "",
            city = "",
            zip = ""
        )
        val props = LocationProperties()

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(true)

        val result = sut?.updateLocation(id, props)
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)

        val inOrder = inOrder(repository, assigner)
        inOrder.verify(repository, times(1))?.findById(id)
        inOrder.verify(assigner, times(1))?.assign(props, obj)
        inOrder.verify(repository, times(1))?.save(obj)
    }

    @Test
    fun testUpdateLocation_NoAuthority() {
        val id = 0L
        val obj = Location(
                address = "",
                city = "",
                zip = ""
        )
        val props = LocationProperties()

        `when`(repository?.findById(id)).thenReturn(Optional.of(obj))
        `when`(authUtil?.hasInstitutionAuthority(SecurityContextHolder.getContext(), -1)).thenReturn(false)

        val result = sut?.updateLocation(id, props)
        Assertions.assertEquals(result?.statusCode, HttpStatus.FORBIDDEN)

        val inOrder = inOrder(repository, assigner)
        inOrder.verify(repository, times(1))?.findById(id)
        inOrder.verify(assigner, never())?.assign(props, obj)
        inOrder.verify(repository, never())?.save(obj)
    }
}