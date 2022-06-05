package com.smartcity.education.backend.authentication

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenVerifier {
    private val parser = Jwts.parser()
    private val logger = java.util.logging.Logger.getLogger(this::class.qualifiedName)

    fun parse(token: String): String? {
        return try {
            val payload = parser.parseClaimsJws(token)
            payload.body["email"].toString()
        } catch (e: Exception) {
            logger.warning("${e.javaClass.name}: ${e.message}")
            null
        }
    }

    fun setSecret(secret: String) {
        parser.setSigningKey(Base64.getEncoder().encodeToString(secret.toByteArray()))
    }
}