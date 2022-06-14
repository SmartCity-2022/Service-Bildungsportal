package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.Constants
import com.smartcity.education.backend.models.AccessToken
import com.smartcity.education.backend.models.RefreshToken
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("\${api.base-path:}")
class TokenApiController(private val restTemplate: RestTemplate) {

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/token"],
        produces = ["application/json"]
    )
    fun refresh(
            @Valid @RequestBody token: RefreshToken
    ): ResponseEntity<AccessToken> {
        val headers = HttpHeaders()

        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.setAccept(listOf(MediaType.APPLICATION_JSON))

        val entity = HttpEntity(token, headers)

        return restTemplate.postForEntity("${Constants.mainHub}/token", entity)
    }
}
