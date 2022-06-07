package com.smartcity.education.backend.authentication

import com.smartcity.education.backend.Constants
import com.smartcity.education.backend.models.User
import com.smartcity.education.backend.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
        authenticationManager: AuthenticationManager?,
        private val verifier: TokenVerifier,
        private val userRepository: UserRepository
) : BasicAuthenticationFilter(authenticationManager) {
    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain
    ) {
        val header = request.getHeader(Constants.Authorization.header)
        if (header == null || !header.startsWith(Constants.Authorization.bearer)) {
            chain.doFilter(request, response)
            return
        }

        val token = header.substring(Constants.Authorization.bearer.length + 1)
        val value = verifier.parse(token)
        if (value == null) {
            chain.doFilter(request, response)
            return
        }

        val user = userRepository.findByIdOrNull(value) ?: userRepository.save(User(value))
        val authorities = mutableListOf<GrantedAuthority>()
        if (user.isAdmin) {
            authorities.add(SimpleGrantedAuthority(Constants.Authorities.admin))
        }
        if (user.institution != null) {
            authorities.add(SimpleGrantedAuthority(Constants.Authorities.institution))
        }
        if (user.student != null) {
            authorities.add(SimpleGrantedAuthority(Constants.Authorities.student))
        }

        val authentication = UsernamePasswordAuthenticationToken(user, null, authorities)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }
}