package com.smartcity.education.backend

import com.smartcity.education.backend.authentication.JwtFilter
import com.smartcity.education.backend.authentication.TokenVerifier
import com.smartcity.education.backend.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig: WebSecurityConfigurerAdapter() {
    @Autowired
    private val verifier: TokenVerifier? = null
    @Autowired
    private val userRepository: UserRepository? = null

    override fun configure(http: HttpSecurity?) {
        http?.run {
            cors().and().csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.GET,
                                "/institution/**",
                                "/location/**",
                                "/education/**"
                        ).permitAll()
                        .antMatchers(HttpMethod.POST,
                                "/token"
                        ).permitAll()
                        .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(JwtFilter(authenticationManager(), verifier!!, userRepository!!), UsernamePasswordAuthenticationFilter::class.java)
        }
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }
}