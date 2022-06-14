package com.smartcity.education.backend.controllers

import com.smartcity.education.backend.authentication.AuthUtil
import com.smartcity.education.backend.models.Matriculation
import com.smartcity.education.backend.models.Student
import com.smartcity.education.backend.models.User
import com.smartcity.education.backend.repositories.MatriculationRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime

@SpringBootTest(classes = [MeApiController::class])
class MeApiControllerTests {
    @MockBean
    private val authUtil: AuthUtil? = null
    @MockBean
    private val matriculationRepository: MatriculationRepository? = null
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

    @Test
    fun testMyMatriculation_IsStudent() {
        val user = User(
                "foo@example.org",
                false,
                student = Student(
                        id = 1,
                        name = ""
                )
        )
        val matriculations = listOf(
                Matriculation(
                    date = LocalDateTime.now()
                )
        )

        `when`(authUtil?.getUser(SecurityContextHolder.getContext())).thenReturn(user)
        `when`(matriculationRepository?.findMatriculationsByStudent_Id(user.student!!.id!!)).thenReturn(matriculations)

        val result = sut?.myMatriculations()
        Assertions.assertEquals(result?.statusCode, HttpStatus.OK)
        Assertions.assertEquals(result?.body, matriculations)

        verify(authUtil, times(1))?.getUser(SecurityContextHolder.getContext())
        verify(matriculationRepository, times(1))?.findMatriculationsByStudent_Id((user.student!!.id!!))
    }

    @Test
    fun testMyMatriculation_IsNotStudent() {
        val user = User(
                "foo@example.org",
                false
        )

        `when`(authUtil?.getUser(SecurityContextHolder.getContext())).thenReturn(user)

        val result = sut?.myMatriculations()
        Assertions.assertEquals(result?.statusCode, HttpStatus.BAD_REQUEST)

        verify(authUtil, times(1))?.getUser(SecurityContextHolder.getContext())
        verify(matriculationRepository, never())?.findMatriculationsByStudent_Id(anyLong())
    }
}