package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder

@SpringBootTest(classes = [MeApiController::class])
class MeApiControllerTests {
    @MockBean
    private val authUtil: AuthUtil? = null
    @Autowired
    private val sut: MeApiController? = null

    @Test
    fun testMe() {
        val user = User(
                "foo@example.org",
                false
        )

        `when`(authUtil?.getUser(SecurityContextHolder.getContext())).thenReturn(user)

        val result = sut?.me()
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, user)

        verify(authUtil, times(1))?.getUser(SecurityContextHolder.getContext())
    }
}