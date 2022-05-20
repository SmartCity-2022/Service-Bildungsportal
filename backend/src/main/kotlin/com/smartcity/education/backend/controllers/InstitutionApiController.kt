package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.assigners.Assigner
import com.smartcity.education.backend.models.Institution
import com.smartcity.education.backend.models.InstitutionProperties
import com.smartcity.education.backend.models.Location
import com.smartcity.education.backend.repositories.InstitutionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class InstitutionApiController {
    @Autowired
    private val repository: InstitutionRepository? = null

    @Autowired
    private val assigner: Assigner<InstitutionProperties, Institution>? = null

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/institution"],
        produces = ["application/json"]
    )
    fun allInstitutions(): ResponseEntity<Iterable<Institution>> {
        val results = repository?.findAll()

        return ResponseEntity
            .ok(results)
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/institution/{id}/location"],
        produces = ["application/json"]
    )
    fun allLocationsOfInstitution(
        @PathVariable("id") id: Long
    ): ResponseEntity<Iterable<Location>> {
        val institution = repository?.findByIdOrNull(id)

        return institution?.let {
            ResponseEntity
                .ok(it.locations)
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/institution"],
        consumes = ["application/json"]
    )
    fun createInstitution(
        @Valid @RequestBody institution: Institution
    ): ResponseEntity<Unit> {
        repository?.save(institution)

        return ResponseEntity
            .created(URI("/institution/${institution.id}"))
            .build()
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/institution/{id}/location"],
        consumes = ["application/json"]
    )
    fun createLocationOfInstitution(
        @PathVariable("id") id: Long,
        @Valid @RequestBody location: Location
    ): ResponseEntity<Unit> {
        val institution = repository?.findByIdOrNull(id)

        return institution?.let {
            location.institution = it
            it.locations.add(location)
            repository?.save(it)

            ResponseEntity
                .created(URI("/location/${location.id}"))
                .build()
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/institution/{id}"],
        produces = ["application/json"]
    )
    fun singleInstitution(
        @PathVariable("id") id: Long
    ): ResponseEntity<Institution> {
        val result = repository?.findByIdOrNull(id)

        return result?.let {
            ResponseEntity
                .ok(it)
        } ?:
            ResponseEntity
                .notFound()
                .build()
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/institution/{id}"],
        consumes = ["application/json"]
    )
    fun updateInstitution(
        @PathVariable("id") id: Long,
        @Valid @RequestBody institutionProperties: InstitutionProperties
    ): ResponseEntity<Unit> {
        val institution = repository?.findByIdOrNull(id)

        return institution?.let {
            assigner?.assign(institutionProperties, it)
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
