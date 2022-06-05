package com.smartcity.education.backend.authentication

import com.smartcity.education.backend.authentication.TokenVerifier
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [TokenVerifier::class])
class TokenVerifierTest {
    @Autowired
    val verifier: TokenVerifier? = null

    @ParameterizedTest
    @CsvSource(
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.mvdjdBZKOtWyQl54xAd8C9kY0RUyq-z26qNTjFR1DKA,a"
    )
    fun testValid(token: String, secret: String) {
        verifier?.setSecret(secret)
        Assertions.assertNotNull(
            verifier?.parse(token)
        )
    }

    @ParameterizedTest
    @CsvSource(
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.6WMVI6W2y3LYr8KfVgoRANRYo2PTpeVX9b5hiasB6qY,a"
    )
    fun testInvalid(token: String, secret: String) {
        verifier?.setSecret(secret)
        Assertions.assertNull(
            verifier?.parse(token)
        )
    }
}