package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.Constants
import com.smartcity.education.backend.assigners.LocationAssigner
import com.smartcity.education.backend.models.Education
import com.smartcity.education.backend.models.Location
import com.smartcity.education.backend.models.LocationProperties
import com.smartcity.education.backend.repositories.LocationRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import java.net.URI

import javax.validation.Valid

import kotlin.collections.List

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class LocationApiController {
    @Autowired
    private val repository: LocationRepository? = null

    @Autowired
    private val assigner: LocationAssigner? = null

    @Autowired
    private val template: RabbitTemplate? = null

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/location/{id}/education"],
        produces = ["application/json"]
    )
    fun allEducationsOfLocation(
        @PathVariable("id") id: Long
    ): ResponseEntity<List<Education>> {
        val location = repository?.findByIdOrNull(id)

        return location?.let {
            ResponseEntity
                .ok(it.educations)
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/location/{id}/education"],
        consumes = ["application/json"]
    )
    fun createEducationOfLocation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody education: Education
    ): ResponseEntity<Unit> {
        val location = repository?.findByIdOrNull(id)

        return location?.let {
            education.location = it
            location.educations.add(education)
            val created = repository?.save(it)

            created?.educations?.last()?.id?.let { id ->
                template?.convertAndSend(
                    Constants.Exchanges.education,
                    Constants.RoutingKeys.created,
                    id.toString()
                )
            }

            ResponseEntity
                .created(URI("/location/${education.id}"))
                .build()
        } ?:
        ResponseEntity
            .notFound()
            .build()
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/location/{id}"],
        produces = ["application/json"]
    )
    fun singleLocation(
        @PathVariable("id") id: Long,
    ): ResponseEntity<Location> {
        val location = repository?.findByIdOrNull(id)

        return location?.let {
            ResponseEntity
                .ok(it)
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/location/{id}"],
        consumes = ["application/json"]
    )
    fun updateLocation(
        @PathVariable("id") id: Long,
        @Valid @RequestBody locationProperties: LocationProperties
    ): ResponseEntity<Unit> {
        val location = repository?.findByIdOrNull(id)

        return location?.let {
            assigner?.assign(locationProperties, it)
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
