package com.smartcity.education.backend.api

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("hello-world")
class HelloWorldController {
    object Data {
        var value: String = "Hello World"
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = [""],
        produces = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun get(): String {
        return Data.value
    }

    @RequestMapping(
        method = [RequestMethod.PUT],
        value = [""],
        consumes = [MediaType.TEXT_PLAIN_VALUE]
    )
    fun set(@RequestBody value: String) {
        Data.value = value
    }
}