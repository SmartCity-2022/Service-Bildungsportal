package com.smartcity.education.backend

import com.smartcity.education.backend.authentication.TokenVerifier
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
class TestJwtConfig {
    @Bean
    fun verifier(): TokenVerifier {
        val verifier = TokenVerifier()
        verifier.setSecret(System.getenv("JWT_SECRET"))
        return verifier
    }

    @Bean
    fun messageSender(): MessageSender {
        return MessageSender { _, _ ->  }
    }
}