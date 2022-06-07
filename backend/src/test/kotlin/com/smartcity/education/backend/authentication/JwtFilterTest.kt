package com.smartcity.education.backend.authentication

import com.smartcity.education.backend.Constants
import com.smartcity.education.backend.models.Institution
import com.smartcity.education.backend.models.Student
import com.smartcity.education.backend.models.User
import com.smartcity.education.backend.repositories.UserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SpringBootTest(classes = [JwtFilter::class])
class JwtFilterTest {
    @Autowired
    private val filter: JwtFilter? = null
    @MockBean
    private val authenticationManager: AuthenticationManager? = null
    @MockBean
    private val verifier: TokenVerifier? = null
    @MockBean
    private val userRepository: UserRepository? = null
    @MockBean
    private val request: HttpServletRequest? = null
    @MockBean
    private val response: HttpServletResponse? = null
    @MockBean
    private val chain: FilterChain? = null

    @AfterEach
    fun tearDown() {
        SecurityContextHolder.getContext().authentication = null
    }

    @Test
    fun testNoHeader() {
        `when`(request?.getHeader(any())).thenReturn(null)

        filter?.doFilter(request!!, response!!, chain!!)

        Assertions.assertNull(SecurityContextHolder.getContext().authentication)

        verify(chain, times(1))?.doFilter(request, response)
    }

    @Test
    fun testInvalidHeader() {
        `when`(request?.getHeader(any())).thenReturn("Basic Foo Bar")

        filter?.doFilter(request!!, response!!, chain!!)

        Assertions.assertNull(SecurityContextHolder.getContext().authentication)

        verify(chain, times(1))?.doFilter(request, response)
    }

    @Test
    fun testInvalidToken() {
        val token = "a"
        `when`(request?.getHeader(Constants.Authorization.header)).thenReturn("Bearer ${token}")
        `when`(verifier?.parse(token)).thenReturn(null)

        filter?.doFilter(request!!, response!!, chain!!)

        Assertions.assertNull(SecurityContextHolder.getContext().authentication)

        verify(chain, times(1))?.doFilter(request, response)
    }

    @Test
    fun testValid_New() {
        val token = "a"
        val value = "b"
        `when`(request?.getHeader(Constants.Authorization.header)).thenReturn("Bearer ${token}")
        `when`(verifier?.parse(token)).thenReturn(value)
        `when`(userRepository?.findById(value)).thenReturn(Optional.empty())
        `when`(userRepository?.save(any())).then { it.arguments.first() }

        filter?.doFilter(request!!, response!!, chain!!)

        val authentication = SecurityContextHolder.getContext().authentication
        Assertions.assertInstanceOf(UsernamePasswordAuthenticationToken::class.java, authentication)
        val principal = authentication.principal
        Assertions.assertInstanceOf(User::class.java, principal)
        val email = (principal as User).email
        Assertions.assertEquals(value, email)
        Assertions.assertFalse(authentication.authorities.any())

        verify(chain, times(1))?.doFilter(request, response)
    }

    @Test
    fun testValid_Admin() {
        val token = "a"
        val value = "b"
        val user = User(
                email = value,
                isAdmin = true
        )
        `when`(request?.getHeader(Constants.Authorization.header)).thenReturn("Bearer ${token}")
        `when`(verifier?.parse(token)).thenReturn(value)
        `when`(userRepository?.findById(value)).thenReturn(Optional.of(user))

        filter?.doFilter(request!!, response!!, chain!!)

        val authentication = SecurityContextHolder.getContext().authentication
        Assertions.assertInstanceOf(UsernamePasswordAuthenticationToken::class.java, authentication)
        val principal = authentication.principal
        Assertions.assertInstanceOf(User::class.java, principal)
        val email = (principal as User).email
        Assertions.assertEquals(value, email)
        Assertions.assertTrue(authentication.authorities.any())
        Assertions.assertTrue(authentication.authorities.all { it.authority.equals(Constants.Authorities.admin) })

        verify(chain, times(1))?.doFilter(request, response)
    }

    @Test
    fun testValid_Student() {
        val token = "a"
        val value = "b"
        val user = User(
                email = value,
                student = Student(name = "")
        )
        `when`(request?.getHeader(Constants.Authorization.header)).thenReturn("Bearer ${token}")
        `when`(verifier?.parse(token)).thenReturn(value)
        `when`(userRepository?.findById(value)).thenReturn(Optional.of(user))

        filter?.doFilter(request!!, response!!, chain!!)

        val authentication = SecurityContextHolder.getContext().authentication
        Assertions.assertInstanceOf(UsernamePasswordAuthenticationToken::class.java, authentication)
        val principal = authentication.principal
        Assertions.assertInstanceOf(User::class.java, principal)
        val email = (principal as User).email
        Assertions.assertEquals(value, email)
        Assertions.assertTrue(authentication.authorities.any())
        Assertions.assertTrue(authentication.authorities.all { it.authority.equals(Constants.Authorities.student) })

        verify(chain, times(1))?.doFilter(request, response)
    }

    @Test
    fun testValid_Institution() {
        val token = "a"
        val value = "b"
        val user = User(
                email = value,
                institution = Institution(name = "")
        )
        `when`(request?.getHeader(Constants.Authorization.header)).thenReturn("Bearer ${token}")
        `when`(verifier?.parse(token)).thenReturn(value)
        `when`(userRepository?.findById(value)).thenReturn(Optional.of(user))

        filter?.doFilter(request!!, response!!, chain!!)

        val authentication = SecurityContextHolder.getContext().authentication
        Assertions.assertInstanceOf(UsernamePasswordAuthenticationToken::class.java, authentication)
        val principal = authentication.principal
        Assertions.assertInstanceOf(User::class.java, principal)
        val email = (principal as User).email
        Assertions.assertEquals(value, email)
        Assertions.assertTrue(authentication.authorities.any())
        Assertions.assertTrue(authentication.authorities.all { it.authority.equals(Constants.Authorities.institution) })

        verify(chain, times(1))?.doFilter(request, response)
    }
}